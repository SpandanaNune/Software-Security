<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
Confirm Transaction
	<br />
<c:out value="${email}"></c:out>
<c:out value="${transactionid}"></c:out>
	<sf:form method="post"
		action="${pageContext.request.contextPath}/completetransaction">
		<table class="formtable">
			<tr>
				<td><input class="control"  name="email" value ="${email}"
						type="hidden" /></td>
			</tr>
			<tr>
				<td><input class="control"  name="transactionid" value ="${transactionid}"
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
		</table>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</sf:form>
</body>
</html>