<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<sf:form method="post"
		action="${pageContext.request.contextPath}/accountactivation"
		commandName="users">

		<table class="formtable">
			<tr>
				<td class="label">UserName:</td>
				<td><sf:input class="control" path="username" name="username"
						type="text" /><br /> <sf:errors path="username" Class="error"></sf:errors></td>
			</tr>

			<tr>
				<td class="label">New Password:</td>
				<td><sf:input class="control" path="password" name="password"
						type="password" /><br /> <sf:errors path="password" Class="error"></sf:errors></td>
			</tr>
			<tr>
				<td class="label">Confirm Password:</td>
				<td><input class="control"  name="confirmpassword"
						type="password" /><br /></td>
			</tr>
			<tr>
				<td class="label"></td>
				<td><input class="control" value="SetUp" type="submit" /></td>
			</tr>
		</table>

	</sf:form>
</body>
</html>