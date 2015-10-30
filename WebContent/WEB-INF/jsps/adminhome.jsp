<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

 <meta name="viewport" content="width=device-width, initial-scale=1">
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/navbar.css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

 <noscript>
   <h2>JavaScript is disabled! Please enable JavaScript in your web browser!</h2>
   <style type="text/css">
     #main-content {
       display: none;
     }
   </style>
 </noscript>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/navbar.css">
 <script language="javascript">
   document.onmousedown = disableclick;
   status = "Right Click Disabled";

   function disableclick(event) {
     if (event.button == 2) {
       alert(status);
       return false;
     }
   }
   var count =0;
   
   function disableMe()
   {
   	alert("Email is being sent!! Please wait to be redirected to the next page!!");
   	if(count ==0)
   		{
   		count =1;
   		return true;
   		}
   	else 
   		return false;
   }
   
   
 </script>


 <script>
   window.location.hash = "no-back-button";
   window.location.hash = "Again-No-back-button";
   window.onhashchange = function() {
     window.location.hash = "no-back-button";
   }
 </script>
 <title>Welcome, Admin</title>
</head>

<body>
 <nav class="navbar navbar-default">
   <div class="container-fluid">
     <div class="navbar-header">
       <a class="navbar-brand" href="${pageContext.request.contextPath}/adminhome">
         <b>MTBC </b>
       </a>
     </div>
     <div>
     
       <ul class="nav navbar-nav navbar-right">
         <li>
          <form method="post"
                        action="${pageContext.request.contextPath}/logout">

                        <input class="btn btn-none" value="Logout" type="submit"
                            style="background-color: #006f87 ; height: 50px; color: white;" />

                        <input type="hidden" name="${_csrf.parameterName}"
                            value="${_csrf.token}" />
                    </form>
         </li>
       </ul>
     </div>

   </div>
 </nav>
 <div class="container">
   <h1>Admin Home</h1>
   <h3>
     Hi
     <c:out value="${uname}"></c:out>
     !
   </h3>
   <div id="border" class="well">
     <center>
       <h4>What would you like to do??</h4>
       <br> <a class="btn btn-info btn-large" style="width: 300px" href="${pageContext.request.contextPath}/employeecreation">Add
                    Employee</a>
       <br>
       <br>
       <br>
       <a class="btn btn-info btn-large" style="width: 300px" href="${pageContext.request.contextPath}/getinternalusers">View/Edit
    Employee</a>
       <br>
       <br>
       <br>
       <a class="btn btn-info btn-large" style="width: 300px" href="${pageContext.request.contextPath}/transactionlog">Transaction
    Logs</a>
       <br>
       <br>
       <br>
       <a class="btn btn-info btn-large" style="width: 300px" href="${pageContext.request.contextPath}/pii">Approve/Reject PII</a>
       <br>
       <br>
       <br>
       <a class="btn btn-info btn-large" style="width: 300px" href="${pageContext.request.contextPath}/systemlogs" onclick="return disableMe();">Mail System Logs</a>
       <br>
       <br>
       <br>
       <a class="btn btn-info btn-large" style="width: 300px" href="${pageContext.request.contextPath}/editadminprofile">Edit
    Profile</a>
       <br>
       <br>
     

       <br>
       <br>
     </center>
   </div>
 </div>
</body>

</html>