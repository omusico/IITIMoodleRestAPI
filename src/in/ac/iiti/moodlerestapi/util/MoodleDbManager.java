package in.ac.iiti.moodlerestapi.util;

import in.ac.iiti.moodlerestapi.admin.resource.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class MoodleDbManager {
	private Connection con=null;
	private PreparedStatement pstmt=null;
	private ResultSet res=null;
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
	 int updateUserRollNumbers(){
		 
		 int rowsUpdated = 0; 
		 
		 if(MySqlDBConnector.isClosed(con)){
			 con = MySqlDBConnector.getConnection();
	     }
		 if(!MySqlDBConnector.isClosed(con)) {
			 try{
				 pstmt = con.prepareStatement("select username, idnumber from mdl_user where idnumber regexp '[^0-9]{7,10}'");
				 res = pstmt.executeQuery();
				 String username;
	             while(res.next())
                 {  
	            	 username = res.getString("username");
	            	 if(username.matches("[a-z]{2,3}[0-9]{7,10}")){
	            		 res.updateString("idnumber", username.replaceAll("[a-z]{2,3}",""));
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
}
