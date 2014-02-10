package in.ac.iiti.moodlerestapi.util;

import in.ac.iiti.moodlerestapi.admin.resource.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CourseHandler {
	private Connection con=null;
	private PreparedStatement pstmt=null;
	private ResultSet res=null;
	
	public synchronized HashMap<String, Course>  getAcademicCourses(String departmentCode){
		HashMap<String, Course> map = new HashMap<String, Course>();
		
		 if(PGSqlDBConnector.isClosed(con)){
			 con = PGSqlDBConnector.getConnection();
	     }
		 if(!PGSqlDBConnector.isClosed(con)) {
			 try{
				 pstmt = con.prepareStatement("select crsecode,crsename,sem,year from  courselist where dept1 = ?");
				 pstmt.setString(1,departmentCode);
				 res = pstmt.executeQuery();
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
		    	 PGSqlDBConnector.closePStatement(pstmt);
	             PGSqlDBConnector.closeResultSet(res);
	             PGSqlDBConnector.closeDBConnection(con);	 
			 }
		 }
		return map;
	}
}
