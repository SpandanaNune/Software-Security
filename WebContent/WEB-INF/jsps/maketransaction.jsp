<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
<head>
<title>Transaction</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript">
	function change() {
		var select = document.getElementById("sendoptions");
		var divv = document.getElementById("mainform");
		var value = select.value;
		if (value == "self") {
			toAppend = "";
			divv.innerHTML = toAppend;
			return;
		}
		if (value == "other") {
			toAppend = "";
			divv.innerHTML = toAppend;
			return;
		}
	}
</script>
</head>
<body>
	<sf:form method="post"
		action="${pageContext.request.contextPath}/createTransaction"
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
			</sf:select> <br> Who do you want to send money to? <br>
			 <select
				class="form-control" id="sendoptions" onchange="change();">
				<option value="self">Your Own Account</option>
				<option value="other">Other Account</option>
			</select> <br /> <br />
			<div id="mainform">
			Select Your Account: <br> <sf:select class='form-control' id='accountnumbersreceive' path='toAccountNo'> <c:forEach var='account' items='${accounts}'><option value='${account.getAccountNo()}'>${account.getAccountNo()}</option></c:forEach> </sf:select> <br>
			
			<!--  Enter The Account Number: <sf:input type='textbox' class='form-control' path='toAccountNo'/> <br>-->
			
			</div>
		</div>
		<br> Enter the amount <br /> 
		<sf:input type='textbox' class='form-control' path='balance'/> <br>
		<button type="submit" class="btn btn-default">Send Money</button>
	</sf:form>
</body>
</html>
