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
		commandName="user">

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
				<td><input class="control" value="Register" type="submit" /></td>
			</tr>
		</table>

	</sf:form>

</body>
</html>