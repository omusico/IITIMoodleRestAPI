package in.ac.iiti.moodlerestapi.admin;

import in.ac.iiti.moodlerestapi.CategoryService;
import in.ac.iiti.moodlerestapi.CourseService;
import in.ac.iiti.moodlerestapi.admin.resource.Course;
import in.ac.iiti.moodlerestapi.util.CourseHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Course
 */
@WebServlet("/admin/course")
public class CourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * This class does the following tasks
     *  1. Get a mapping between categoryId and category Name in a HashMap with category Name as the key.
     *  2. Get a list of courses being offered from the academic database in a Map with courseName as key.
     *     Also append the other codes of same offering in the course code. 
     *  3. Get a list of current Courses in moodle created after specified date
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
        
        Calendar calendar = Calendar.getInstance();
        DateFormat dfm = new SimpleDateFormat("yyyyMMddHHmm");
        int currentTime = (int)(calendar.getTimeInMillis()/1000); 
        int unixSemStartTimeStamp=0;
        
        session.setAttribute("currentYear", year);
        session.setAttribute("currentSemester", semester);
        
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
        //evaluate timestamp for moodle
        
                 
		HashMap<String, Course> acadCourseInfo = null;
		CourseHandler courseHandler = new CourseHandler();

        // get the academic data
        acadCourseInfo = courseHandler.getAcademicCourses(departmentCode);
        //get the list of moodle courses 
        JsonArray moodleCourses = new CourseService().getCourses((String)session.getAttribute("token"));
        System.out.println("<<<<<<<<<<<<<-----------------Iter Moodle Course-------------------->>>>>>>>>>>>");

        for(JsonValue jsonValue: moodleCourses){
        	String moodleCourseName =  ((JsonObject)jsonValue).getString("fullname");
        	System.out.println("courseName = "+ moodleCourseName);
        	if(acadCourseInfo.containsKey(moodleCourseName)){
        		System.out.println("course Exists");
        		int startDate =  ((JsonObject)jsonValue).getInt("startdate");
        		System.out.println("moodleCourseStart Date = "+startDate);
        		System.out.println("semStartDate = "+unixSemStartTimeStamp);
        		System.out.println("current Timestamp = "+ currentTime);
        		if(unixSemStartTimeStamp <= startDate){ //if the course has been published in moodle for given semester
        			Course existingCourse = acadCourseInfo.get(moodleCourseName);
        			existingCourse.setPublishedInMoodle(true);
        		}
        		}
        	else{
        		System.out.println("course does not Exists");
        	}
         }
           session.setAttribute("acadCourseInfo", acadCourseInfo);
           response.sendRedirect("courses.jsp");
	  }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("in dopost method of courses");
		HttpSession session = request.getSession();
		HashMap<String,Integer> categoryMap = new HashMap<String,Integer>();
		//TODO catch exception
        JsonArray categoryArray = new CategoryService().getCategories((String)session.getAttribute("token"));
        //iterate through the array and get list of 
        for(JsonValue jsonValue: categoryArray){
        	categoryMap.put(((JsonObject)jsonValue).getString("name"), ((JsonObject)jsonValue).getInt("id"));
        }
        
		//validate the admin
		String role = (String) session.getAttribute("role");
		if(role==null || !role.equalsIgnoreCase("admin")){
			return; //TODO
		}
	    String courseNames[] = request.getParameterValues("selectedCourses");
	    System.out.println("selected courses are :\n"+courseNames.toString());
	    for(int i=0;i<courseNames.length;i++){
	    	System.out.println("------------"+ courseNames[i]);
	    }
		HashMap<String, Course> acadCourseInfo = (HashMap<String, Course>) session.getAttribute("acadCourseInfo");
		
		 Calendar calendar = Calendar.getInstance();
		
		StringBuilder paramBuilder = new StringBuilder("");
		int year = (int)session.getAttribute("currentYear");
		int semester = (int)session.getAttribute("currentSemester");
		String semName="";
		if(semester==1){
			semName = "Spring";
		}
		else{
			semName = "Autumn";
		}
		
		int courseCount = 0;
		int currentTime = (int)(calendar.getTimeInMillis()/1000);
		for(String courseName : courseNames){
			if(acadCourseInfo.containsKey(courseName)){
				Course course = acadCourseInfo.get(courseName);
				paramBuilder.append(
						"&courses["+courseCount +"][fullname]=" + URLEncoder.encode(course.getFullname(), "UTF-8")+
						"&courses["+courseCount +"][shortname]="+ URLEncoder.encode(course.getShortname()+"("+semName+""+year+")", "UTF-8")+
						"&courses["+courseCount +"][startdate]=" + URLEncoder.encode(currentTime+"", "UTF-8") + 
						"&courses["+courseCount +"][idnumber]=" + URLEncoder.encode(course.getFullname()+semester+""+year, "UTF-8")
						);
//				"&courses[0][categoryid]="+ URLEncoder.encode("2", "UTF-8");
				
				if(course.getYear()==null){
					int categoryId = categoryMap.get("Others");
					paramBuilder.append("&courses["+courseCount +"][categoryid]="+ URLEncoder.encode(categoryId+"", "UTF-8"));
					System.out.println("added course -nn---"+ course.getFullname()+ "\n CourseCount = "+courseCount);
				}
				else{
					String yr = course.getYear();
					String sem = course.getSemester();
					int categoryId = categoryMap.get("B.Tech_Year-"+course.getYear()+"_Sem-"+course.getSemester());
					paramBuilder.append("&courses["+courseCount +"][categoryid]="+ URLEncoder.encode(categoryId+"", "UTF-8"));
					System.out.println("added course -nn---"+ course.getFullname() + "\n CourseCount = "+courseCount);
				}
				courseCount++;
			}
		}
		String urlParams = paramBuilder.toString();
		
		new CourseService().createCourses(urlParams, (String)session.getAttribute("token"));
		
		// remove unnecessary attributes
		session.removeAttribute("acadCourseInfo");
		session.removeAttribute("currentYear");
		session.removeAttribute("currentSemester");
		
		response.sendRedirect("../admin/adminhome.jsp");
	   }
        
	}

	
																				