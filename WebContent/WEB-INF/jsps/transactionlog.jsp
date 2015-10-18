<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	Transaction Log
	<h2>Filter Logs by</h2>

	<sf:form method="POST" action="${pageContext.request.contextPath}/transactionlog" commandName="transactionLog" >
		<sf:errors path="*" cssClass="errorblock" element="div" />
		<table>
			<tr>
				<td><sf:radiobutton class="control" path="logFilter" value="date" name="By Date"/>By Date 
				<sf:radiobutton
						path="logFilter" class="control" value="account" name="By AccountNo"/>By AccountNo
						<sf:radiobutton
						path="logFilter" class="control" value="name" name ="By Name"/>By Name</td>
				<td><sf:errors class="control" path="logFilter" cssClass="error" /></td>
			</tr>
			<tr>
				<td class="label">Date: (MM/DD/YYYY)</td>
				<td><sf:input class="control" path="date" name="date"
						type="text" /><br /> <sf:errors path="date" Class="error"></sf:errors></td>
			</tr>
			<tr>
				<td class="label">Account No:</td>
				<td><sf:input class="control" path="accountNo" name="accountNo"
						type="text" /><br /> <sf:errors path="accountNo" Class="error"></sf:errors></td>
			</tr>
			<tr>
				<td class="label">User Name:</td>
				<td><sf:input class="control" path="name" name="name"
						type="text" /><br /> <sf:errors path="accountNo" Class="error"></sf:errors></td>
			</tr>
			<tr>
				<td colspan="3"><input class="control" type="submit" /></td>
			</tr>

		</table>
		
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
	</sf:form>
</html>