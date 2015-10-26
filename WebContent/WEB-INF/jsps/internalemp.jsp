<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<title>Internal Employee</title>
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
</head>
<body>
	<div class="container">
		<H1>Internal Employee Home</H1>
		<h3>
			Hi
			<c:out value="${user.getUsername()}"></c:out>
			!
		</h3>
		<div id="border" class="well">
			<center>

				<h4>What would you like to do??</h4>
				<br> <a class="btn btn-info btn-large" style="width:300px"
					href="${pageContext.request.contextPath}/bankers">View
					Transactions</a> <br /> <br /> <br> <a
					class="btn btn-info btn-large" style="width: 300px"
					href="${pageContext.request.contextPath}/viewedituserdetails_employee">View/Edit
					Customer Details</a> <br /> <br /> <br> <a
					class="btn btn-info btn-large" style="width: 300px"
					href="${pageContext.request.contextPath}/editemployeeprofile">Edit
					Profile</a>


			</center>
		</div>
	</div>
</body>
</body>
</html>
