<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<link href="${pageContext.request.contextPath}/static/css/main.css"
	rel="stylesheet" type="text/css" />

<title>Approve Transactions</title>
</head>
<body>
  <div class="container">
	<h1>Approve Transactions</h1>

	<table class="offers table table-hover">
		<tr>
		<TH>Transaction Id</TH>
			<TH>Account No</TH>
			<TH>Transaction Type</TH>
			<TH>Amount</TH>
			<TH>Approve</TH>
      <th></th>
		</tr>

		<c:forEach var="transaction" items="${transaction}">
			<tr>
			<TD><c:out
						value="${transaction.getPrimaryKey().getTransactionId()}" /></TD>
				<TD><c:out
						value="${transaction.getPrimaryKey().getAccountNo()}" /></TD>
										<TD><c:out value="${transaction.getTransactionType()}" /></TD>
					
				<TD><c:out value="${transaction.getAmount()}" /></TD>

				<td><form method="post"
						action="${pageContext.request.contextPath}/accepttransactionbtn">
						<input type="hidden" name="Accept" value="${transaction.getPrimaryKey().getTransactionId()}" />
						<input class="control btn btn-success" value="Accept" type="submit" /> <input
							type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form></td>
				<td><form method="post"
						action="${pageContext.request.contextPath}/deletetransactionbtn">
						<input type="hidden" name="Decline" value="${transaction.getPrimaryKey().getTransactionId()}" />
						<input class="control btn btn-danger" value="Decline" type="submit" /> <input
							type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form></td>

			</tr>
		</c:forEach>
	</table>
</div>
</body>
</html>