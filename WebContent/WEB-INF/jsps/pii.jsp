<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<title>PII</title>
</head>
<body>
  <div class = "container">
	<H1>PII List</H1>
	<TABLE class = "offers table table-hover">
		<TR>
			<TH>User Name</TH>
			<TH>Old SSN</TH>
			<TH>New SSN</TH>
			<TH>User Type</TH>
			
			<TH>Approve / Decline</TH>
      <TH></TH>
		</TR>
		<c:forEach items="${piis}" var="pii">
			<TR>
				<TD><c:out
						value="${pii.getUserName()}" /></TD>
				<TD><c:out
						value="${pii.getOldSSN()}" /></TD>
				<TD><c:out value="${pii.getNewSSN()}" /></TD>
				<c:if test="${pii.isMerchant()==true}">
								<TD>MERCHANT</TD>
				</c:if>
				<c:if test="${pii.isMerchant()==false}">
								<TD>CUSTOMER</TD>
				</c:if>
				<td><form method="post"
						action="${pageContext.request.contextPath}/acceptpii">
						<input type="hidden" name="Accept" value="${pii.getUserName()}" />
						<input class="control btn btn-success" value = "Accept" type="submit" /> <input
							type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
             
					</form></td> <td><form method="post"
						action="${pageContext.request.contextPath}/declinepii">
						<input type="hidden" name="Decline" value="${pii.getUserName()}" />
						<input class="control btn btn-danger" value = "Decline" type="submit" /> <input
							type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form></td>

			</TR>
		</c:forEach>

	</TABLE>
	<!--  <button class="btn btn-primary btn-large" type="submit" onClick="showAlert()">Download</button>
	 	 <button class="btn btn-primary btn-large" type="submit" onClick="showAlert()">E-Mail</button> -->
   </div>
</body>
</html>
