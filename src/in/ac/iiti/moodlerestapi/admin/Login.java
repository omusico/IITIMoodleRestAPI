package in.ac.iiti.moodlerestapi.admin;

import in.ac.iiti.moodlerestapi.util.AppConfigProperty;
import in.ac.iiti.moodlerestapi.util.Commons;
import in.ac.iiti.moodlerestapi.util.AcadDbManager;
import in.ac.iiti.moodlerestapi.util.MoodleDbManager;
import in.ac.iiti.moodlerestapi.LoginInternalService;
import in.ac.iiti.moodlerestapi.LoginService;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Properties;

import javax.json.JsonObject;
import javax.json.JsonString;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class Login
 */
@WebServlet("/adminlogin")
public class Login extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static Properties  propertyInstance = AppConfigProperty.getPropertyInstance();
	private static String adminUsername = propertyInstance.getProperty("adminUsername");
	
    public Login() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
	    	
		try{
			//get the username and password
			String username = request.getParameter("username");
	        String password = request.getParameter("password");
	    		    	
	        JsonObject tokenJson = new LoginInternalService().getToken(username,password);
	        String token = null;
	        
	        try{
	        	token=tokenJson.getString("token");
	        }catch(NullPointerException exception){
	        	String error = tokenJson.getString("error");
	        	if(error!=null && error.equals("The username was not found in the database")){
	        	    session.setAttribute("loginError", "Incorrect username or password");
	            }
	            else{
	            	session.setAttribute("loginError", error);
	            }
	        	response.sendRedirect("#login");
	        	return;
	        }
	        
	        //check if username is that of the admin by calling core_webservice_get_site_info function
	        
	        JsonObject jsonObject=null;
	        
	        try{
	            jsonObject= Commons.getJsonObject("GET",null, token, "core_webservice_get_site_info");
	        } 
	         catch(javax.ws.rs.ProcessingException connectException){
	        	session.setAttribute("loginError", "Invalid Auth Token");
	        	response.sendRedirect("");
	        	//TODO redirect to guest page
	        }
	        // if true then set a session attribute for them and store the token
	        String name = jsonObject.getString("username");
	        
	        if(name!=null && name.equals(adminUsername)){ // TODO
	        	session.setAttribute("role","admin");
	        	session.setAttribute("token", token);
	        	//Dispatch him to the admin home site
	        	response.sendRedirect("admin/adminhome.jsp");
	        }
	        //TODO else inform him about the unsuccessful login 
	        else{
	        	session.setAttribute("loginError", "Incorrect username or password");
	        	response.sendRedirect("");
	        }   
        }
        catch(java.net.ConnectException connectionException){
        	session.setAttribute("connectErrorCode",1); //moodle server connect exception
        	response.sendRedirect("errors/connectionerror.jsp");
        }
        
	}
}
