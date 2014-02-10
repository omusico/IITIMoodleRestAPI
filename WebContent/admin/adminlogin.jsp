<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
String errMessage=(String)session.getAttribute("error");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IITI Moodle ReST API: Login</title>
        <link href="style/formpage.css" rel="stylesheet" type="text/css" media="screen" /> 
    </head>
    <body>
        <div id="basic-form">
        <form action="../adminlogin" method="post">
            <fieldset>
                <legend>Admin Login</legend>
                <dl>
                <dt>
                <label for="username">Username</label>
                </dt>
                <dd><input type="text" name="username" id="username"> </dd>
                <dt>
                <label for="password">Password</label>
                </dt>
                <dd><input type="password" name="password" id="password">
                </dd>
            </dl>
                <p><input type="submit" value="Login">
                <%
                if(errMessage=="invalid"){
                //if(true){
                %>
                Invalid username or password !
                <%}
                %>
                </p>
             </fieldset>
        </form>
       </div>
    </body>
</html>