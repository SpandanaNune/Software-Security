<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<sf:form method="post"
		action="${pageContext.request.contextPath}/debitCreditToAccount"
		commandName="transactionDetails" htmlEscape="true">
		<div class="container">
			<h1>Transfer Funds</h1>
			<h3>
				Hi <c:out value="${user.getUsername()}"></c:out>!
			</h3>
			<br> Select Your Account <br /> <br />
			 <sf:select class="form-control" id="accountnumbers" path="fromAccountNo" >
				<c:forEach var="account" items="${accounts}">
					<option  value="${account.getAccountNo()}">${account.getAccountNo()}</option>
				</c:forEach>
			</sf:select>  <br>
			
			 <sf:select path='transaction_type'
				class="form-control" id="sendoptions" >
				<option value="DEBIT">Debit</option>
				<option value="CREDIT">Credit</option>
			</sf:select> <br /> <br />
			
			
		</div>
		<br> Enter the amount <br /> 
		<sf:input type='textbox' class='form-control' path='balance'/> <br>
		<button type="submit" class="btn btn-default">Make Transaction</button>
	</sf:form>
</body>
</html>