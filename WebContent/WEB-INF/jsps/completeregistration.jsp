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
	Confirm Registration
	<br />
	<sf:form method="post"
		action="${pageContext.request.contextPath}/registerbtn2">
		<table class="formtable">
			<tr>
				<td><input class="control"  name="mail" value ="${mail}"
						type="hidden" /></td>
			</tr>
			<tr>
				<td class="label">Enter OTP:</td>
				<td><input class="control"  name="otpValue"
						type="text" />
				</td>
			</tr>
			<tr>
				<td class="label"></td>
				<td><input class="control" value="Verify" type="submit" /></td>
			</tr>
			<tr>
				<td class="label"></td>
				<td><input class="label"  name="otpstatus" value ="${otpstatus}"/>
				</td>
			</tr>
		</table>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</sf:form>
</body>
</html>
