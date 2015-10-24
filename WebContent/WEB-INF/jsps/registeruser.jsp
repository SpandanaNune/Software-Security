<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link href="${pageContext.request.contextPath}/static/css/main.css"
	rel="stylesheet" type="text/css" />
</head>
<body>
	User Registration page
	<br />

	<sf:form method="post"
		action="${pageContext.request.contextPath}/registerbtn"
		commandName="user" htmlEscape="true">

		<table class="formtable">
			<tr>
				<td class="label">UserName:</td>
				<td><sf:input class="control" path="username" name="username"
						type="text" /><br /> <sf:errors path="username" Class="error"></sf:errors></td>
			</tr>

			<tr>
				<td class="label">FirstName:</td>
				<td><sf:input class="control" path="firstname" name="firstname"
						type="text" /><br /> <sf:errors path="firstname" Class="error"></sf:errors></td>
			</tr>
			<tr>
				<td class="label">LastName:</td>
				<td><sf:input class="control" path="lastname" name="lastname"
						type="text" /><br /> <sf:errors path="lastname" Class="error"></sf:errors></td>
			</tr>
			<tr>
				<td class="label">Email:</td>
				<td><sf:input class="control" path="email" name="email"
						type="text" /><br /> <sf:errors path="email" Class="error"></sf:errors></td>
			</tr>
			<tr>
				<td class="label"></td>
				<td><input class="control" value="Verify" type="button" /></td>
			</tr>
			<tr>
				<td class="label">Phone:</td>
				<td><sf:input class="control" path="phone" name="phone"
						type="text" /><br /> <sf:errors path="phone" Class="error"></sf:errors></td>
			</tr>
			<tr>
				<td class="label">SSN:</td>
				<td><sf:input class="control" path="SSN" name="SSN" type="text" /><br />
					<sf:errors path="SSN" Class="error"></sf:errors></td>
			</tr>
			<tr>
				<td class="label">Date of Birth (mm/dd/yyyy):</td>
				<td><sf:input class="control" path="dob" name="dob" type="text" /><br />
					<sf:errors path="dob" Class="error"></sf:errors></td>
			</tr>

			<tr>
				<td class="label">Address Line 1:</td>
				<td><sf:input class="control" path="Addr1" name="Addr1"
						type="text" /><br /> <sf:errors path="Addr1" Class="error"></sf:errors></td>
			</tr>

			<tr>
				<td class="label">Address Line 2:</td>
				<td><sf:input class="control" path="Addr2" name="Addr2"
						type="text" /><br /> <sf:errors path="Addr2" Class="error"></sf:errors></td>
			</tr>
			<tr>
				<td class="label">City:</td>
				<td><sf:input class="control" path="City" name="City"
						type="text" /><br /> <sf:errors path="City" Class="error"></sf:errors></td>
			</tr>
			<tr>
				<td class="label">State:</td>
				<td><sf:input class="control" path="State" name="State"
						type="text" /><br /> <sf:errors path="State" Class="error"></sf:errors></td>
			</tr>
			<tr>
				<td class="label">ZIP Code:</td>
				<td><sf:input class="control" path="Zip" name="Zip" type="text" /><br />
					<sf:errors path="Zip" Class="error"></sf:errors></td>
			</tr>
			<tr>
				<td class="label"></td>
				<td><input class="control" value="Register" type="submit" /></td>
			</tr>
		</table>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</sf:form>

</body>
</html>


