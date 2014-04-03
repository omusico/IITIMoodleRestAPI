package in.ac.iiti.moodlerestapi.admin;

import in.ac.iiti.moodlerestapi.util.AcadDbManager;
import in.ac.iiti.moodlerestapi.util.MoodleDbManager;

import java.io.IOException;
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        /*
         * First do the 2 backend processing jobs 
         *  1. Update the moodle user table (mdl_user table) to store rollno of students derived from username
         *  2. migrate the enrollment records from academic database to moodle mysql database
         */
        MoodleDbManager manager = new MoodleDbManager();
		manager.updateUserRollNumbers(); //backend task 1
		AcadDbManager acadDbManager = new AcadDbManager();
		acadDbManager.migrateAcadRecords(year, semester); //backend task 2
		// enroll all students of given dept, semester and year for all courses
        int enrolledCount = manager.enrolAllStudents(departmentCode, semester, year, (String)session.getAttribute("token"));
		
		session.setAttribute("currentYear", year);
        session.setAttribute("currentSemester", semester);
        if(enrolledCount>0){
            session.setAttribute("enrolSuccess", "Success !! "+enrolledCount+ " enrolments done");
        }
        else{
        	session.setAttribute("enrolSuccess", "No enrollment done");
        }
        	
        response.sendRedirect("../admin/adminhome.jsp");
   }


}
