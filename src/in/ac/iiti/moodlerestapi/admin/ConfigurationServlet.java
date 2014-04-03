package in.ac.iiti.moodlerestapi.admin;

import in.ac.iiti.moodlerestapi.util.AppConfigProperty;
import in.ac.iiti.moodlerestapi.util.AcadDbManager;
import in.ac.iiti.moodlerestapi.util.MoodleDbManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ConfigurationServlet
 */
@WebServlet("/sysconfig")
public class ConfigurationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In config servlet ");
		//get the session
		HttpSession session = request.getSession();
		//validate the admin
		String role = (String) session.getAttribute("role");
		if( request.getServletContext().getInitParameter("firstLoad").equals("0") && 
				(role==null || !role.equalsIgnoreCase("admin"))){
			return; 
		}
		
		HashMap<String,String> configMap = new HashMap<>();
		String acadPostGreServerUrl = request.getParameter("acadPostGreServerUrl");
		if(acadPostGreServerUrl!=null && !acadPostGreServerUrl.equals("")){
			configMap.put("acadPostGreServerUrl", acadPostGreServerUrl);
		}
		String acadDbName = request.getParameter("acadDbName");
		if(acadDbName!=null && !acadDbName.equals("")){
			configMap.put("acadDbName", acadDbName);
		}		
		String acadPostGreServerUsername = request.getParameter("acadPostGreServerUsername");
		if(acadPostGreServerUsername!=null && !acadPostGreServerUsername.equals("")){
			configMap.put("acadPostGreServerUsername", acadPostGreServerUsername);
		}		
		String acadPostGreServerPassword = request.getParameter("acadPostGreServerPassword");
		if(acadPostGreServerPassword!=null && !acadPostGreServerPassword.equals("")){
			configMap.put("acadPostGreServerPassword", acadPostGreServerPassword);
		}		
		String moodleServerUrl = request.getParameter("moodleServerUrl");
		if(moodleServerUrl!=null && !moodleServerUrl.equals("")){
			configMap.put("moodleServerUrl", moodleServerUrl);
		}
		////
		String moodleMySqlUrl = request.getParameter("moodleMySqlUrl");
		if(moodleMySqlUrl!=null && !moodleMySqlUrl.equals("")){
			configMap.put("moodleMySqlUrl", moodleMySqlUrl);
		}
		String moodleMySqlUsername = request.getParameter("moodleMySqlUsername");
		if(moodleMySqlUsername!=null && !moodleMySqlUsername.equals("")){
			configMap.put("moodleMySqlUsername", moodleMySqlUsername);
		}
		String moodleMySqlPassword = request.getParameter("moodleMySqlPassword");
		if(moodleMySqlPassword!=null && !moodleMySqlPassword.equals("")){
			configMap.put("moodleMySqlPassword", moodleMySqlPassword);
		}
		String moodleDbName = request.getParameter("moodleDbName");
		if(moodleDbName!=null && !moodleDbName.equals("")){
			configMap.put("moodleDbName", moodleDbName);
		}
		String adminUsername = request.getParameter("adminUsername");
		if(adminUsername!=null && !adminUsername.equals("")){
			configMap.put("adminUsername", adminUsername);
		}
		
		Properties propertyInstance = AppConfigProperty.getPropertyInstance();
		AppConfigProperty.getAppConfigPropertyInstance().updateProperties(configMap);
		
		//tell that the servlet is not loaded the first time.
		request.getServletContext().setAttribute("firstLoad", "false");
		
	    //redirect to home page 	
		response.sendRedirect("./");
	}
}
