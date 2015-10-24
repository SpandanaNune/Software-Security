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
	Forgot Password page
	<br />

	<sf:form method="post"
		action="${pageContext.request.contextPath}/resetpasswordbtn"
		commandName="users">

		<table class="formtable">
			<tr>
				<td class="label">New Password:</td>
				<td><sf:input class="control" path="password" name="password"
						type="text" value=""/><br /> <sf:errors path="password" Class="error"></sf:errors></td>
			</tr>
			<tr>
				<td class="label">Confirm Password:</td>
				<td><input class="control"  name="confirmpassword"
						type="text" /><br /></td>
			</tr>
			<tr>
				<td class="label"></td>
				<td><input class="control" value="Register" type="submit" /></td>
			</tr>
		</table>

	</sf:form>

</body>
</html>