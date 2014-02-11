<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <%
     
     int errorCode= (Integer)session.getAttribute("connectErrorCode");
     String errorString = "Server Error"; 
     if(errorCode==1){
    	 errorString = "Could not connect to moodle server. Please contact server administrator";
     }
     else if(errorCode==2){
    	 errorString = "Could not connect to MySql Server of moodle server.  Please contact server administrator";
     }
     session.removeAttribute("connectErrorCode");
 %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IITI: Moodle Rest API</title>
</head>
<body>
<h4><%=errorString %></h4>
</body>
</html>