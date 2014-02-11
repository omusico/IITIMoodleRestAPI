<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    <title>IITI: Moodle Rest API</title>
    <link type="text/css" rel="stylesheet" href="style/main.css" />
    <link type="text/css" rel="stylesheet" href="style/table.css" />
    <link type="text/css" rel="stylesheet" href="style/mainform.css" />
    <script language="javascript" src="scripts/jquery-1.9.1.min.js"></script>
	<script language="javascript" src="scripts/main.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
		init();
		$("#login").click();
		});
    </script>
  </head>
  
  <%
     String errMessage=(String)session.getAttribute("loginError");
     if(errMessage!=null){
    	 session.removeAttribute("loginError");
     }
     // if application is loaded for the first time
     if(application.getAttribute("firstLoad")==null){
    	 response.sendRedirect("./admin/configure.jsp");
     }
  %>
  <body>
  
    <div id="wrap">
      <div id="container">
        
        <!-- HEADER STARTS -->
        <div id ="header">
		        <div id="toprightlogo">
		   	        <div id="headerleft"><h3>Admin & Dev Portal (IITI Moodle ReST API) </h3></div>
		   	        <div id="headerright"><img  src ="logo1.gif" alt="Not Available" width="300px" height="60px"></div>
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
				      <a id="login" class="tab" href="#login">Log in</a> 
				  </li>
				</ul>
       </div>
         <!-- HEADER END -->
       
        <div id="content">
          <div id="main">
           <%@ include file="../default.html" %>
           <%@ include file="../apidocs.html" %>
          
           <div id="admin-tab" class="g-unit">
            <h2>You must be signed in as administrator</h2>
           </div>
		    
	        <%@ include file="../aboutus.html" %>
		   
            <!-- Login tab STARTS-->
            <div id="login-tab" class="g-unit" >
            <form action="./adminlogin" method="post" class="forms">
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
                if(errMessage !=null){
                //if(true){
                %>
                 <%= errMessage%>
                <% }
                %>
                </p>
               </fieldset>
              </form>
           </div>
		   
           </div>
         </div> 
         <%@ include file="../includes/footer.jsp" %>
        </div>
      </div>
    </body>
</html>
