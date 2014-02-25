package in.ac.iiti.moodlerestapi.admin;

import in.ac.iiti.moodlerestapi.CourseService;
import in.ac.iiti.moodlerestapi.admin.resource.Course;
import in.ac.iiti.moodlerestapi.util.CourseHandler;
import in.ac.iiti.moodlerestapi.util.MoodleDbManager;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class EnrolServlet
 */
@WebServlet("/admin/enrol")
public class EnrolServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EnrolServlet() {
        super();
    }
    /**
     * Validate the user.
     * Get a list of current Courses in moodle created after specified date
     * Display them in 
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get the session
		HttpSession session = request.getSession();
		//validate the admin
		String role = (String) session.getAttribute("role");
		if(role==null || !role.equalsIgnoreCase("admin")){
			return;//TODO
		}
		
		//retrieve the input parameters
        int semester = Integer.parseInt(request.getParameter("semester")); // 1- jan to may, 2 - july to december
        int year = Integer.parseInt(request.getParameter("year")); 
        String departmentCode = request.getParameter("department");
        
        
        MoodleDbManager manager = new MoodleDbManager();
		manager.enrolAllStudents(departmentCode, semester, year, (String)session.getAttribute("token"));
		
		session.setAttribute("currentYear", year);
        session.setAttribute("currentSemester", semester);
        
        
		
   }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
