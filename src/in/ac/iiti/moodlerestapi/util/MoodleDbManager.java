package in.ac.iiti.moodlerestapi.util;

import in.ac.iiti.moodlerestapi.EnrolService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class MoodleDbManager {
	private static Connection con=null;
	private static PreparedStatement pstmt=null;
	private static ResultSet res=null;
	private static Properties  emailInstance = AppConfigProperty.getEmailInstance(); 

	/**
	 * This function updates the idnumber field in mdl_user table to the students roll number
	 * This is by using username field in mdl_user as an input.
	 *    1.All students who enrolled after 2010 in the institute have their username as their deptName 
	 *    appended with their roll number. 
	 *    2. For rest we have a mapping(username->rollno) that is stored in email.properties file
	 *    
	 * Algorithm
	 *   1. Get all the usernames and idnumber from mdl_user which are not null in a resultSet
	 *   2. Iterate through the resultSet processing each username at a time.
	 *   3.     If username matches  regex "[a-z]{2,3}[0-9]{7,10}" i.e (as user enrolled after 2010)
	 *            then extract the digit part and update it in the idnumber field of the resultset
	 *   4.     else
	 *            look for the username in emailProperties 
	 *               if found, update the idnumber field of resultSet
	 *               else, do nothing
	 *  @return: number of rows updated
	 */
	 public static int updateUserRollNumbers(){
		 
		 int rowsUpdated = 0; 
		 
		 if(MySqlDBConnector.isClosed(con)){
			 con = MySqlDBConnector.getConnection();
	     }
		 if(!MySqlDBConnector.isClosed(con)) {
			 try{
				 pstmt = con.prepareStatement("select id, username, idnumber,lastaccess,city,country,firstname,lastname,email,mnethostid,deleted,confirmed,auth  from mdl_user where idnumber not regexp '[0-9]{7,10}'"
						                      ,ResultSet.TYPE_SCROLL_INSENSITIVE ,ResultSet.CONCUR_UPDATABLE);
				 res = pstmt.executeQuery();
				 String username;
				 String roll;
	             while(res.next())
                 {  
	            	 username = res.getString("username");
	            	 if(username.matches("[a-z]{2,3}[0-9]{7,10}")){
	            		 roll = username.replaceAll("[a-z]{2,3}","");
	            		 System.out.println("ROll is "+ roll);
	            		 res.updateString("idnumber", roll);
	            		 res.updateRow();
	            		 rowsUpdated++;
	            	 }
	            	 else{
	                     String idnumber = emailInstance.getProperty(username);
	                     if(idnumber != null){
	                         res.updateString("idnumber",idnumber);
	                         res.updateRow();
	                         rowsUpdated++;
	                     }
	                 }
                 }
	             
			 }catch(SQLException sqle){
		          sqle.printStackTrace();
		     }finally{
		    	 MySqlDBConnector.closePStatement(pstmt);
	             MySqlDBConnector.closeResultSet(res);
	             MySqlDBConnector.closeDBConnection(con);	 
			 }
		 }

		return rowsUpdated;	
	}
	 
	public synchronized int enrolAllStudents(String departmentCode, int semester, int year, String wstoken) throws IOException{
		int enrolCount=0;
		
		Calendar calendar = Calendar.getInstance();
        DateFormat dfm = new SimpleDateFormat("yyyyMMddHHmm");
        int currentTime = (int)(calendar.getTimeInMillis()/1000); 
        int unixSemStartTimeStamp=0;
        
        
        String timeStart=null;
        if(semester == 1){
        	timeStart = year+"01010000";
        }
        else if(semester == 2){
        	timeStart = year+"07010000";
        }
        try {
			unixSemStartTimeStamp =  (int)(dfm.parse(timeStart).getTime()/1000);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
        if(MySqlDBConnector.isClosed(con)){
			 con = MySqlDBConnector.getConnection();
	     }
		 if(!MySqlDBConnector.isClosed(con)) {
			 try{
				 String deptRegexp = ".*["+departmentCode.toUpperCase()+"].*";
				 pstmt = con.	prepareStatement("select mdl_user.id as userid,mdl_course.id as courseid, rollno "+ 
                                                 "from acad_enrol, mdl_course,mdl_user "+
                                                 " where acad_enrol.idnumber = mdl_course.idnumber " +
                                                    "and  acad_enrol.coursecode REGEXP '.*["+departmentCode+"].*'" +
                                                    "and  mdl_course.startdate >  ? and mdl_user.idnumber = acad_enrol.rollno");
				 
		         pstmt.setInt(1, unixSemStartTimeStamp);
		         
		         res = pstmt.executeQuery();
		         StringBuilder paramBuilder = new StringBuilder("");
		         int count = 0;
		         while(res.next()){
		        	 System.out.println(res.getString("userid")+" -- "+ res.getString("courseid"));
						paramBuilder.append(
								"&enrolments["+count +"][roleid]=" + URLEncoder.encode(5+"", "UTF-8")+
								"&enrolments["+count +"][userid]="+ URLEncoder.encode(res.getString("userid"), "UTF-8")+
								"&enrolments["+count +"][courseid]=" + URLEncoder.encode(res.getString("courseid"), "UTF-8")  
								);
						count++;
		         }
		         if(count>0){
		        	 new EnrolService().enrolUsers(paramBuilder.toString(), wstoken);
		         } 
			 }catch(SQLException sqle){
		          sqle.printStackTrace();
		     }finally{
		    	 MySqlDBConnector.closePStatement(pstmt);
	             MySqlDBConnector.closeResultSet(res);
	             MySqlDBConnector.closeDBConnection(con);	 
			 }
	   }
	   return enrolCount;
   } // end of function
}