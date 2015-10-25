<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<H1>PII List</H1>
	<TABLE BORDER="1">
		<TR>
			<TH>User Name</TH>
			<TH>Old SSN</TH>
			<TH>New SSN</TH>
			<TH>Approve / Decline</TH>
		</TR>
		<c:forEach items="${piis}" var="pii">
			<TR>
				<TD><c:out
						value="${pii.getUserName()}" /></TD>
				<TD><c:out
						value="${pii.getOldSSN()}" /></TD>
				<TD><c:out value="${pii.getNewSSN()}" /></TD>
				<td><form method="post"
						action="${pageContext.request.contextPath}/acceptpii">
						<input type="hidden" name="Accept" value="${pii.getUserName()}" />
						<input class="control" value = "Accept" type="submit" /> <input
							type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form><form method="post"
						action="${pageContext.request.contextPath}/declinepii">
						<input type="hidden" name="Decline" value="${pii.getUserName()}" />
						<input class="control" value = "Decline" type="submit" /> <input
							type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form></td>

			</TR>
		</c:forEach>

	</TABLE>
	<!--  <button class="btn btn-primary btn-large" type="submit" onClick="showAlert()">Download</button>
	 	 <button class="btn btn-primary btn-large" type="submit" onClick="showAlert()">E-Mail</button> -->
	 
</body>
</html>