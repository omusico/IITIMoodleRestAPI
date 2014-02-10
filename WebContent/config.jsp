<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Properties"%>
<%@page import="in.ac.iiti.moodlerestapi.util.AppConfigProperty"%>
<%@page import="java.io.InputStream"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Configurations</title>

</head>
<body>
<!--  
InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");  

Properties configProperties = new Properties();  


// load the inputStream using the Properties  
configProperties.load(inputStream);  
// get the value of the property  

%> -->
<%Properties configProperties = new Properties();

configProperties.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));

%>
String url = <%= configProperties.getProperty("acadPostGreServerUrl")%> <br/>
String dbName =  <%= configProperties.getProperty("acadDbName")%><br/>
static String userName =  <%= configProperties.getProperty("acadPostGreServerUsername")%><br/> 
static String password =  <%= configProperties.getProperty("acadPostGreServerPassword")%><br/>
</body>
</html>