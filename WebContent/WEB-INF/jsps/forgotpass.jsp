<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forgot Password</title>
<script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>

	<br />

	<sf:form method="post"
		action="${pageContext.request.contextPath}/forgotPassEmailSuccess">

		<table class="formtable">
			<tr>
				<td class="label">Enter email:</td>
				<td><input class="control"  name="email"
						type="text" /><br /></td>
			</tr>
			<tr>
				<td class="label">Verify Captcha:</td>
				<td><div class="g-recaptcha" data-sitekey="6LcHCw8TAAAAAIqGUaZBHaZbJ4ra61tME5Lz3zB7"></div></td>
			</tr>
			
			<tr>
				<td class="label"></td>
				<td><input class="control" value="Register" type="submit" /></td>
			</tr>
		</table>
	Please select a file to upload : <input type="file" name="file" />
		<input type="submit" value="upload" />
	</sf:form>
	<a href="${pageContext.request.contextPath}/login">Login</a>
</body>
</html>