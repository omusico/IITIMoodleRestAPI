<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    <title>IITI: Moodle Rest API</title>
    <%@ page import="in.ac.iiti.moodlerestapi.admin.resource.Course" %>
    <%@ page import="java.util.HashMap" %>
    <%@ page import="java.util.Iterator" %>
    <link type="text/css" rel="stylesheet" href="../style/main.css" />
    <link type="text/css" rel="stylesheet" href="../style/table.css" />
    <script language="javascript" src="../scripts/jquery-1.9.1.min.js"></script>
	<script language="javascript" src="../scripts/main.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
		init();
		$("#admin").click();
		});
    </script>
  </head>
  <body>
  <%
    if(session.getAttribute("role")==null || session.getAttribute("role") != "admin"){
    	response.sendRedirect("./");
    }  
  %>
    <div id="wrap">
      <div id="container">
               <!-- HEADER STARTS -->
        <div id ="header">
		        <div id="toprightlogo">
		   	        <div id="headerleft"><h2>IITI Moodle Rest API</h2></div>
		   	        <div id="headerright"><img  src ="../logo1.gif"  " alt="Not Available" width="300px" height="60px"></div>
		   	        <div style="clear:both;"></div>
		          </div>
		      </div>
		            <div id="menubar" class="nav">
			      <ul>
				  <li>
				      <a id="home" class="tab" href="#home">Home</a>  
				  </li>
				  <li>
				      <a id="apidocs"  class="tab" href="#apidocs" onclick="showRateForm">API docs</a> 
				  </li>
				  <li>
				      <a id="admin" class="tab" href="#admin">Administration</a>
				  </li>
				  <li>
				      <a id="about" class="tab" href="#about">About us</a> 
				  </li>
				  <li>
				      <a class="tab" href=".././adminlogout">Log out</a>
				  </li>
				  
				</ul>
       </div>
       <!-- HEADER END -->
       
       <div id="content">
          <div id="main">
           <%@ include file="../default.html" %>
           <%@ include file="../apidocs.html" %>
           <div id="admin-tab" class="g-unit">
           
           <!--Create a table with all courses- name and check box which is disabled if a course is already existent -->
           <!-- and a submit button that posts data to -->          
            <div  id="course-list">
              <form id="rate-form" action="../admin/course" method="post">  
                  <table style="border: 1px solid #ccc; width: 640px">
			      <thead>
				  <tr>
				      <th width="5%">S. No.</th>
				      <th width="40%">Course Name</th>
				      <th width="20%">Course Code</th>
				      <th width="10%">Year</th>
				      <th width="10%">Semester</th>
				      <th width="10%">Publish</th>
				  </tr>
			      </thead>
			      <tbody>
			      <%
			      HttpSession adminSession = request.getSession();
			      HashMap<String, Course> acadCourseInfo = (HashMap<String, Course>)session.getAttribute("acadCourseInfo");
// 			      List<Entity> movies = (List<Entity>)request.getAttribute("movieList");
			      int courseCount =1;
			      if(acadCourseInfo == null){System.out.println("courseList is null");}
			      
			      if(acadCourseInfo != null){

			    	  Iterator itr = acadCourseInfo.keySet().iterator();
			    	  
			    	  while(itr.hasNext())
			    	  {
			    	      String key = (String)itr.next();
			    	      Course course =  acadCourseInfo.get(key);
			    	      
			        	//if we have reached the end of list of movies to be rated
			        %>
			         <tr>
			         <td><%=courseCount %>.</td>
				      <td><%= course.getFullname()%></td>
				      <td><%= course.getShortname()%></td>
				      <td><%= course.getYear()%></td>
				      <td><%= course.getSemester()%></td>
				      <td>
				         <% if(course.getPublishedInMoodle()){ %>
				        	 <input type="checkbox" name="selectedCourses" value="<%=course.getFullname() %>" id="val<%=courseCount %>"  disabled/>
				         <% } else{%>
				         <input type="checkbox" name="selectedCourses" value="<%=course.getFullname() %>" id="val<%=courseCount%>"/>
				         <%} %>
				      </td>
				     </tr>
			         
			        <%courseCount++;  		   
			         }
			         }  
			        %>
				  <tr>
				      <td> - </td>
				      <td><p><input type="submit" value="Publish Courses"></p></td>
				      <td>-</td>
				   </tr>
			      </tbody>
			      <tfoot>
			      </tfoot>
		          </table>
		       </form>
		<!-- end form  -->
		     </div>
           </div>
		  
		   
		   
		   <%@ include file="../aboutus.html" %>
           </div>

           </div> 
           <%@ include file="../includes/footer.jsp" %>
            </div>
         </div>
    </body>
</html>