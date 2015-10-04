<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script language="javaScript">
function showAlert()
{
 
  alert('File Download Complete');
}
</script>
</head>
<body>
<sf:form method="post" action="${pageContext.request.contextPath}/downloadTransactions">
	<H1>Transaction History</H1>
	<TABLE BORDER="1">
		<TR>
			<TH>Transaction Id</TH>
			<TH>Account No</TH>
			<TH>Transaction Type</TH>
			<TH>Amount</TH>
			<TH>Status</TH>
		</TR>
		<c:forEach items="${transactions}" var="transaction">
			<TR>
				<TD><c:out
						value="${transaction.getPrimaryKey().getTransactionId()}" /></TD>
				<TD><c:out
						value="${transaction.getPrimaryKey().getAccountNo()}" /></TD>
				<TD><c:out value="${transaction.getAmount()}" /></TD>
				<TD><c:out value="${transaction.getTransactionType()}" /></TD>
				<TD><c:out value="${transaction.getStatus()}" /></TD>

			</TR>
		</c:forEach>

	</TABLE>
	<!--  <button class="btn btn-primary btn-large" type="submit" onClick="showAlert()">Download</button>
	 	 <button class="btn btn-primary btn-large" type="submit" onClick="showAlert()">E-Mail</button> -->
	 
	 </sf:form>
	  <a class="btn btn-primary btn-large"
		href="${pageContext.request.contextPath}/downloadTransactions" type="submit"> Download</a> 
		  <a class="btn btn-primary btn-large"
		href="${pageContext.request.contextPath}/emailTransactions" type="submit"> Email </a> 
</body>
</html>