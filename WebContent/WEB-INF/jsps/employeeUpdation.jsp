<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>Add Employee</title>
</head>
<body>
	<div class="container">
		<h1>Add an Employee</h1>

		<sf:form method="post"
			action="${pageContext.request.contextPath}/employeeupdationdone"
			commandName="user" htmlEscape="true">

			<table class="formtable">
				<tr>
					<td><sf:input class="control form-control"  path="username"
							name="username" type="hidden" /><br /> <sf:errors path="username"
							Class="error"></sf:errors></td>
				</tr>

				<tr>
					<td class="label"><h4 style="color: black">FirstName:</h4></td>
					<td><sf:input class="control form-control" path="firstname"
							name="firstname" type="text" /><br /> <sf:errors
							path="firstname" Class="error"></sf:errors></td>
				</tr>
				<tr>
					<td class="label"><h4 style="color: black">LastName:</h4></td>
					<td><sf:input class="control form-control" path="lastname"
							name="lastname" type="text" /><br /> <sf:errors path="lastname"
							Class="error"></sf:errors></td>
				</tr>
				<tr>
					<td><sf:input class="control form-control" path="email"
							name="email" type="hidden" /><br /> <sf:errors path="email"
							Class="error"></sf:errors></td>
				</tr>
				<tr>
					<td class="label"><h4 style="color: black">Phone:</h4></td>
					<td><sf:input class="control form-control" path="phone"
							name="phone" type="text" /><br /> <sf:errors path="phone"
							Class="error"></sf:errors></td>
				</tr>
				<tr>
					<td><sf:input class="control form-control" path="SSN"
							name="SSN" type="hidden" /><br /> <sf:errors path="SSN"
							Class="error"></sf:errors></td>
				</tr>
				<tr>
					<td class="label"><h4 style="color: black">Date of Birth
							(mm/dd/yyyy):</h4></td>
					<td><sf:input class="control form-control" path="dob"
							name="dob" type="text" /><br /> <sf:errors path="dob"
							Class="error"></sf:errors></td>
				</tr>

				<tr>
					<td class="label"><h4 style="color: black">Address Line
							1:</h4></td>
					<td><sf:input class="control form-control" path="Addr1"
							name="Addr1" type="text" /><br /> <sf:errors path="Addr1"
							Class="error"></sf:errors></td>
				</tr>

				<tr>
					<td class="label"><h4 style="color: black">Address Line
							2:</h4></td>
					<td><sf:input class="control form-control" path="Addr2"
							name="Addr2" type="text" /><br /> <sf:errors path="Addr2"
							Class="error"></sf:errors></td>
				</tr>
				<tr>
					<td class="label"><h4 style="color: black">City:</h4></td>
					<td><sf:input class="control form-control" path="City"
							name="City" type="text" /><br /> <sf:errors path="City"
							Class="error"></sf:errors></td>
				</tr>
				<tr>
					<td class="label"><h4 style="color: black">State:</h4></td>
					<td><sf:input class="control form-control" path="State"
							name="State" type="text" /><br /> <sf:errors path="State"
							Class="error"></sf:errors></td>
				</tr>
				<tr>
					<td class="label"><h4 style="color: black">ZIP Code:</h4></td>
					<td><sf:input class="control form-control" path="Zip"
							name="Zip" type="text" /><br /> <sf:errors path="Zip"
							Class="error"></sf:errors></td>
				</tr>

				
			</table>


			<br>
			<br>
			<center>
			<input class="control btn btn-info" value="Update" type="submit" />
	</center>

		</sf:form>

	</div>
</body>
</html>