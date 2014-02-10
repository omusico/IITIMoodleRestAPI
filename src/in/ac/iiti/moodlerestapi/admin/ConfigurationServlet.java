package in.ac.iiti.moodlerestapi.admin;

import in.ac.iiti.moodlerestapi.util.AppConfigProperty;

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
			return; //TODO
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
		System.out.println("Config: Getting properties\n");
		Properties propertyInstance = AppConfigProperty.getPropertyInstance();
		AppConfigProperty.getAppConfigPropertyInstance().updateProperties(configMap);
		System.out.println("Config: Redirect properties are \n"+propertyInstance.toString());
		
		//tell that the servlet is not loaded the first time.
		request.getServletContext().setAttribute("firstLoad", "false");
		
		System.out.println("Config: Sending Redirect to guesthome");
		
		response.sendRedirect("./");
		
		System.out.println("Config: Redirect sent to guesthome");
	}
}
