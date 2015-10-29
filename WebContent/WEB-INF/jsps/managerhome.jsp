<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/static/css/main.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>


<title>Manager Home</title>
</head>
<body>
  <div class="container">
	<H1>Manager Home</H1>
  <h3> Hi <c:out value="${user.getUsername()}"></c:out> ! </h3>
  <div id="border" class="well">
    
      <h4> What would you like to do??</h4> <br>
			<a class="btn btn-info btn-large" style="width:300px"
				href="${pageContext.request.contextPath}/usersignuprequest">User
				SignUp Request</a><br /> <br />  <br>
        <a class="btn btn-info btn-large" style="width:300px"
				href="${pageContext.request.contextPath}/viewedituserdetails">View/Edit User
				Details</a><br /> <br /> <a class="btn btn-primary btn-large"
				href="${pageContext.request.contextPath}/deleteactiveusers">Delete User</a><br /> <br />
				<a class="btn btn-primary btn-large"
				href="${pageContext.request.contextPath}/approvetransaction">Approve Transaction</a><br /> <br /> 
				<a class="btn btn-primary btn-large"
				href="${pageContext.request.contextPath}/merchantsignuprequest">Merchant Sign Up Request</a><br /> <br />
				<br /> 
				<a class="btn btn-primary btn-large"
				href="${pageContext.request.contextPath}/editmanagerprofile">Edit Profile</a><br /> 
		
	</div>
</div>

</body>


</html>
