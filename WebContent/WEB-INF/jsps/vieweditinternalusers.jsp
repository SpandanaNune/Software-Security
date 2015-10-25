<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link href="${pageContext.request.contextPath}/static/css/main.css"
	rel="stylesheet" type="text/css" />
​
<title>View/Edit User Details</title>
</head>
<body>
	<div class="container">
		<h1>View / Edit User Details</h1>
		<table class="offers table table-hover">
			<tr>
				<th>UserName</th>
				<th>FirstName</th>
				<th>LastName</th>
				<th>Email</th>
								<th>Role</th>
				
				<th>Action</th>
				<th></th>
			</tr>
			​
			<c:forEach var="user" items="${user}" varStatus="stat">
				<tr>
					<td><c:out value="${user.getUsername()}"></c:out></td> ​
					<td><c:out value="${user.getFirstname()}"></c:out></td> ​
					<td><c:out value="${user.getLastname()}"></c:out></td> ​
					<td><c:out value="${user.getEmail()}"></c:out></td>
						<td><c:out value="${roles[stat.index]}"></c:out></td>
					
					<td>	<form method="post"
						action="${pageContext.request.contextPath}/editemployee">
						<input type="hidden" name="View/Edit" value="${user.getUsername()}" />
						
						<input class="control btn btn-info" value = "View/Edit" type="submit" /> <input
							type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form></td><td>
					<form method="post"
						action="${pageContext.request.contextPath}/deleteemployee">
						<input type="hidden" name="Decline" value="${user.getUsername()}" />
						<input class="control btn btn-danger" value = "Delete" type="submit" /> <input
							type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>
					</td>
				
				</tr>
			</c:forEach>
		</table>
	</div>
</body>

</html>