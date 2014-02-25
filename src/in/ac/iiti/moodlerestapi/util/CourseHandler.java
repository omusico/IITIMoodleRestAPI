package in.ac.iiti.moodlerestapi.util;

import in.ac.iiti.moodlerestapi.admin.resource.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CourseHandler {
	private Connection con=null;
	private PreparedStatement postGresPstmt=null;
	private ResultSet res=null;
	
	public synchronized HashMap<String, Course>  getAcademicCourses(String departmentCode){
		HashMap<String, Course> map = new HashMap<String, Course>();
		
		 if(PGSqlDBConnector.isClosed(con)){
			 con = PGSqlDBConnector.getConnection();
	     }
		 if(!PGSqlDBConnector.isClosed(con)) {
			 try{
				 postGresPstmt = con.prepareStatement("select crsecode,crsename,sem,year from  courselist where dept1 = ?");
				 postGresPstmt.setString(1,departmentCode);
				 res = postGresPstmt.executeQuery();
	             while(res.next())
                 {  
	            	 String courseName = res.getString("crsename");
	            	 // if a different Id of the same course exists then add it to course shortname
	            	 if(map.containsKey(courseName)){
	            		 Course course = map.get(courseName);
	            		 course.setShortname(course.getShortname()+"/"+res.getString("crsecode"));
	            	 }
	            	 else{	 
		            	 Course course = new Course();
		            	 course.setFullname(res.getString("crsename"));
		            	 course.setShortname(res.getString("crsecode"));
		            	 course.setSemester(res.getString("sem"));
		            	 course.setYear(res.getString("year"));
		            	 map.put(courseName, course);
	            	 }
                 }
	             
			 }catch(SQLException sqle){
		          sqle.printStackTrace();
		     }finally{
		    	 PGSqlDBConnector.closePStatement(postGresPstmt);
	             PGSqlDBConnector.closeResultSet(res);
	             PGSqlDBConnector.closeDBConnection(con);	 
			 }
		 }
		return map;
	}
	
	/**
	 * This function retrieves the enrollment data from academic database (Postgres)
	 * @param currentYear:  currentYear in which enrollments need to be made
	 * @param currentSem: 1 for jan-may and 2 for July to Nov   
	 * 
	 * Mapping between (current year, currentSem) asked to admin and that present in database (academic Year, sem)
	 * 
	 *  if(currentSem===1) the acadSem =2 and acadYear = currentYear - 1 ;
	 *  else                   acadSem =2 and acadYear = currentYear;  
	 */
	public synchronized int migrateAcadRecords(int currentYear, int currentSem){
		
		
	    int rowsMigrated = 0;	
	    int acadYear=0;
	    int acadSem=0;
	    // get the acadYear and acadSem of academic database from the current Year and semester
	    if(currentSem==1){
	    	acadSem = 2;
	    	acadYear = currentYear-1;
	    }
	    else if(currentSem == 2) {
	    	acadSem =1;
	    	acadYear = currentYear;
	    }
	    String timeSuffix = currentSem+""+currentYear;
	    
	    // get a connection from academic database
		if(PGSqlDBConnector.isClosed(con)){
			 con = PGSqlDBConnector.getConnection();
	     }
		if(!PGSqlDBConnector.isClosed(con)) {
			 try{
				 	 postGresPstmt = con.prepareStatement("select courselist.crsename as courseName,courselist.crsecode as courseCode,"+
			                        " student_curr_course22.rollno from courselist,student_curr_course22"+
							        " where courselist.crsecode = student_curr_course22.crsecode"+
			                        " and student_curr_course22.acadyear = ? and student_curr_course22.acadsem = ?");
					 postGresPstmt.setInt(1,acadYear);
					 postGresPstmt.setInt(2,acadSem);
					 res = postGresPstmt.executeQuery(); 
					 
					 Connection moodleMySqlConnection = MySqlDBConnector.getConnection();
					 /*first remove all data from existing acad_enrol mysql table*/
					 PreparedStatement moodleMySqlPstmt = moodleMySqlConnection.prepareStatement("delete from acad_enrol");
					 moodleMySqlPstmt.executeUpdate();
					 /*Now update the table with fresh data*/
					 moodleMySqlPstmt = moodleMySqlConnection.prepareStatement("insert into acad_enrol values (?,?,?)");
					 // now here get a mysql connection 
		             while(res.next())
	                 {  
		                moodleMySqlPstmt.setString(1, res.getString("coursename")+timeSuffix);
		                moodleMySqlPstmt.setString(2, res.getString("rollno"));
		                moodleMySqlPstmt.setString(3, res.getString("coursecode"));
		                rowsMigrated+=moodleMySqlPstmt.executeUpdate();
		             }
             }catch(SQLException sqle){
		          sqle.printStackTrace();
		     }finally{
		    	 PGSqlDBConnector.closePStatement(postGresPstmt);
	             PGSqlDBConnector.closeResultSet(res);
	             PGSqlDBConnector.closeDBConnection(con);	 
			 }
	   }
       return rowsMigrated;	
   } // end of function
}// end of class	