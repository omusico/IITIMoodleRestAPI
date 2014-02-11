<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Properties"%>
<%@ page import="in.ac.iiti.moodlerestapi.util.AppConfigProperty" %>
<html>
  <head>
    <title>IITI: Moodle Rest API</title>
    <link type="text/css" rel="stylesheet" href="../style/main.css" />
    <link type="text/css" rel="stylesheet" href="../style/table.css" />
    <link type="text/css" rel="stylesheet" href="../style/mainform.css" />
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
      	response.sendRedirect(".././");
      }
  %>
    <div id="wrap">
      <div id="container">
               <!-- HEADER STARTS -->
        <div id ="header">
		        <div id="toprightlogo">
		   	        <div id="headerleft"><h3>Admin & Dev Portal (IITI Moodle ReST API) </h3></div>
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
           <%@ include file="../default1.html" %>
           <%@ include file="../apidocs.html" %>
           <div id="admin-tab" class="g-unit">
             <div class="courseCreation forms" >
              <form action="../admin/course" method="get"> 
              <fieldset>
                 <legend>1. Enter details for course list</legend>
                <dl>
	                <dt>
	                    <label for="department">Department</label>
	                </dt>
	                <dd>
	                      <select name="department" id="department" tabindex="1">
                              <option value="CS">Computer Science</option>
                              <option value="EE">Electrical Engineering</option>
                              <option value="ME">Mechanical Engineering</option>
                          </select>
	                </dd>
	                
	                <dt>
	                    <label for="semester">Semester</label>
	                </dt>
	                <dd>
	                      <select name="semester" id="semester" tabindex="2">
                              <option value="1">Spring(Jan-May)</option>
                              <option value="2">Autumn(July-Nov)</option>
                          </select>
	                </dd>
	                
	                <dt>
	                    <label for="year">Year</label>
	                </dt>
	                <dd>
	                      <select name="year" id="year" tabindex="2">
                             <%
                             	for(int yearIndex = 2013;yearIndex <= 2099;yearIndex++) {
                             %>
                              <option value="<%=yearIndex%>"><%=yearIndex%></option>
                             <%
                             	}
                             %>
                                         
                          </select>
	                </dd>
	                
                </dl>
                <p><input type="submit" value="Get Courses">
                
                </p>
             </fieldset>
          </form>
         </div>
            <div class="courseEnrollment">
               <h4> 2. Course Enrollment </h4>
            </div> 
           
           <div class="sysConfig forms" >   
            <%
            	Properties propertyInstance = AppConfigProperty.getPropertyInstance();
            %>
            
            <form action="../sysconfig" method="post"> 
              <fieldset>
                 <legend>3. Site configuration properties</legend>
                <dl>
	                <dt>
	                    <label for="moodleServerUrl" >Moodle Server Url</label> 
	                </dt>
	                <dd>
	                     <input type="text" name="moodleServerUrl" value="<%=propertyInstance.getProperty("moodleServerUrl") %>" />
	                </dd>
	                <dt>
	                    <label for="acadPostGreServerUrl" >Academic-Server Url (PostgreSQL)</label> 
	                </dt>
	                <dd>
	                     <input type="text" name="acadPostGreServerUrl" value="<%=propertyInstance.getProperty("acadPostGreServerUrl") %>" />
	                </dd>
	                
	                <dt>
	                    <label for="acadPostGreServerUsername" >Academic-Server UserName </label> 
	                </dt>
	                <dd>
	                     <input type="text" name="acadPostGreServerUsername" value="<%=propertyInstance.getProperty("acadPostGreServerUsername") %>" />
	                </dd>
	                
	                <dt>
	                    <label for="acadPostGreServerPassword" >Academic Server Password</label> 
	                </dt>
	                <dd>
	                     <input type="password" name="acadPostGreServerPassword" value="<%=propertyInstance.getProperty("acadPostGreServerPassword") %>" />
	                </dd>
	                
	                <dt>
	                    <label for="acadDbName" >Academic Database Name</label> 
	                </dt>
	                <dd>
	                     <input type="password" name="acadDbName" value="<%=propertyInstance.getProperty("acadDbName") %>" />
	                </dd>
	                
	            </dl>
	              <p><input type="submit" value="Set Properties"></p>
             </fieldset>
          </form>
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
