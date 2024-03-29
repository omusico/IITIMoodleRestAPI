<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="in.ac.iiti.moodlerestapi.util.AppConfigProperty" %>   
<%@page import="java.util.Properties"%>
<%

    // load only if it is admin or app is loaded for first time
	
    if(application.getAttribute("firstLoad")==null || 
    		                          session.getAttribute("role") == "admin"){
        Properties propertyInstance = AppConfigProperty.getPropertyInstance();
%>
 

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
		   <!-- HEADER END -->
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
				      <a class="tab" href="#">Log in</a> 
				  </li>
				</ul>
       </div>
       
        
        <div id="content">
          <div id="main">
           <%@ include file="../default1.html" %>
           <%@ include file="../apidocs.html" %>
          
           <div id="admin-tab" class="g-unit">
            <div class="sysConfig forms">
            <form action="../sysconfig" method="post"> 
              <fieldset>
                 <legend>This is sites first access, enter configuration details</legend>
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
	                     <input type="text" name="acadDbName" value="<%=propertyInstance.getProperty("acadDbName") %>" />
	                </dd>
	                <!-- mysql moodle server data -->
	                <dt>
	                    <label for="moodleMySqlUrl" > Moodle MySql Url</label> 
	                </dt>
	                <dd>
	                     <input type="text" name="moodleMySqlUrl" value="<%=propertyInstance.getProperty("moodleMySqlUrl") %>" />
	                </dd>
	                
	                <dt>
	                    <label for="moodleMySqlUsername" >Moodle MySql UserName </label> 
	                </dt>
	                <dd>
	                     <input type="text" name="moodleMySqlUsername" value="<%=propertyInstance.getProperty("moodleMySqlUsername") %>" />
	                </dd>
	                
	                <dt>
	                    <label for="moodleMySqlPassword" >Mysql Server Password</label> 
	                </dt>
	                <dd>
	                     <input type="password" name="moodleMySqlPassword" value="<%=propertyInstance.getProperty("moodleMySqlPassword") %>" />
	                </dd>
	                
	                <dt>
	                    <label for="moodleDbName" >Moodle Database Name</label> 
	                </dt>
	                <dd>
	                     <input type="text" name="moodleDbName" value="<%=propertyInstance.getProperty("moodleDbName") %>" />
	                </dd>
	                
	                <dt>
	                    <label for="adminUsername" >Admin Username</label> 
	                </dt>
	                <dd>
	                     <input type="text" name="adminUsername" value="<%=propertyInstance.getProperty("adminUsername") %>" />
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
<%
}else{
	response.sendRedirect(".././");
}%>