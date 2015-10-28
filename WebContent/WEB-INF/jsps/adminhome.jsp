<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Admin Home</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link href="${pageContext.request.contextPath}/static/css/navbar.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/static/css/main.css"
	rel="stylesheet" type="text/css" />

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>Admin Home</title>
</head>
<body>
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
				<br> <a class="btn btn-info btn-large" style="width: 300px"
					href="${pageContext.request.contextPath}/employeecreation">Add
					Employee</a> <br> <br> <br> <a
					class="btn btn-info btn-large" style="width: 300px"
					href="${pageContext.request.contextPath}/getinternalusers">View/Edit
					Employee</a> <br> <br> <br> <a
					class="btn btn-info btn-large" style="width: 300px"
					href="${pageContext.request.contextPath}/transactionlog">Transaction
					Logs</a> <br> <br> <br> <a
					class="btn btn-info btn-large" style="width: 300px"
					href="${pageContext.request.contextPath}/pii">Approve PII</a> <br>
				<br> <br> <a class="btn btn-info btn-large"
					style="width: 300px"
					href="${pageContext.request.contextPath}/editadminprofile">Edit
					Profile</a>
				<form method="post"
					action="${pageContext.request.contextPath}/logout">
					<input class="control btn btn-info" value="Logout" type="submit" />
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>

				<br> <br>
			</center>
		</div>
	</div>
</body>
</html>
