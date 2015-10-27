<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
	<sf:form method="post"
		action="${pageContext.request.contextPath}/makePaymentTransaction"
		commandName="transactionDetails" htmlEscape="true"
		enctype="multipart/form-data">

		<div class="container">
			<h1>Make Payment</h1>
			<h3>
				Hi
				<c:out value="${user.getUsername()}"></c:out>
				!
			</h3>
			<br> Select Your Account <br /> <br />
			<sf:select class="form-control" id="accountnumbers"
				path="fromAccountNo">
				<c:forEach var="account" items="${accounts}">
					<option value="${account.getAccountNo()}">${account.getAccountNo()}</option>
				</c:forEach>
			</sf:select>
			<sf:errors path="fromAccountNo" Class="error"></sf:errors>
			<br> Who do you want to send money to? <br>

			<sf:select class="form-control" id="sendoptions"
				path="toOtherAccountNo">
				<c:forEach var="toaccount" items="${toaccounts}">
					<option value="${toaccount.getAccountNo()}">${toaccount.getAccountNo()}</option>
				</c:forEach>
			</sf:select>
			<sf:errors path="toOtherAccountNo" Class="error"></sf:errors>
			<br /> <br /> <br> Enter the amount <br />
			<sf:input type='textbox' class='form-control' path='balance' />
			<br>
			<sf:errors path="balance" Class="error"></sf:errors>


		</div>
		<button type="submit" class="btn btn-default">Send Money</button>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</sf:form>
</body>
</html>