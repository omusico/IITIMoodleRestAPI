<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IITI: Configure</title>
</head>
<body>
 <div class="sysConfig">
            <h2>First Access</h2>   
            <h4>Please enter site configuration properties</h4>
            <form action="../sysconfig" method="post"> 
              <fieldset>
                 <legend>Enter Configuration details</legend>
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
	                    <label for="moodleMySqlPassword" >Academic Server Password</label> 
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
	                
	            </dl>
                <p><input type="submit" value="Set Properties"></p>
             </fieldset>
          </form>
           </div>
</body>
</html>
<%
}else{
	response.sendRedirect(".././");
}%>