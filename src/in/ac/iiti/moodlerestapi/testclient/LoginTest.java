package in.ac.iiti.moodlerestapi.testclient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.json.JsonObject;

import org.eclipse.jdt.internal.compiler.flow.FinallyFlowContext;

import in.ac.iiti.moodlerestapi.*;
import in.ac.iiti.moodlerestapi.util.*;

public class LoginTest {
	 private Connection con=null;;
	 private PreparedStatement pstmt=null;
	 private ResultSet res=null;
	 
	 public void dbTest(String departmentCode) throws SQLException, ClassNotFoundException{
		 
		 if(PGSqlDBConnector.isClosed(con)){
//			 con = PGSqlDBConnector.getConnection();
			 Class.forName("org.postgresql.Driver");
			 con = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/acaddb", "postgres","harshit");
		 }
		 if(!PGSqlDBConnector.isClosed(con)) {
			 try{
//				 pstmt = con.prepareStatement("select crsecode,crsename,sem,year from  courselist where dept1 = ?");
//				 pstmt.setString(1,departmentCode);
				 pstmt = con.prepareStatement("select crsecode,crsename,sem,year from  public.courselist");
//				 pstmt= con.prepareStatement("select * from public.studentdetail_pk ");
//				 pstmt= con.prepareStatement("SELECT VERSION() ");
	             res = pstmt.executeQuery();
	             System.out.println("query executed and result set obtained res size is"+res.getRow());
                 while(res.next())
                 {  
                	System.out.println("iterating in resultset"); 
                    System.out.println(res.getString("crsecode"));
//                	System.out.println(res.getString(1));
                }
			 }catch(SQLException sqle){
		          sqle.printStackTrace();
		     }finally{
		    	 PGSqlDBConnector.closePStatement(pstmt);
	             PGSqlDBConnector.closeResultSet(res);
	             PGSqlDBConnector.closeDBConnection(con);	 
			 }
		 }
		 
	 }
	 
    public static void main(String args[]) throws IOException, SQLException, ClassNotFoundException{
//        JsonObject response = new LoginService().getToken("admin", "Root123#");
    	  JsonObject response = new LoginService().getToken("harshit", "1413mshr2028");
        System.out.println("Token is "+ response.toString());
//    	new CourseService().getCourses("c58818df1be7c557e5552a4142903eb8").toString();
//    	new CourseService().getCourseContents("2","c58818df1be7c557e5552a4142903eb8");
//    	new CourseService().getCourseParticipants("2","c58818df1be7c557e5552a4142903eb8");
//    	new CategoryService().getCategories("c58818df1be7c557e5552a4142903eb8");
//    	new UserService().getUserDetailbyId(("c58818df1be7c557e5552a4142903eb8"),2);
//    	new ForumService().getAllAccessibleForums("ca101fe527f200510303aabdb1781e79");
//    	new ForumService().getForumDiscussions("1", "ca101fe527f200510303aabdb1781e79");
    	
    	//test db connections
//    	new LoginTest().dbTest("CS");
    	
//    	new CourseHandler().getAcademicCourses("CS");
    	
    		
    	}


    }

