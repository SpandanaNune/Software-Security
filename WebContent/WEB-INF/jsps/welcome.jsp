<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="navbar.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<noscript>
	<h2>JavaScript is disabled! Please enable
		JavaScript in your web browser!</h2>
	<style type="text/css">
#main-content {
	display: none;
}
</style>
</noscript>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/navbar.css">
<script language="javascript">
	document.onmousedown = disableclick;
	status = "Right Click Disabled";

	function disableclick(event) {
		if (event.button == 2) {
			alert(status);
			return false;
		}
	}
</script>

<script>
	window.location.hash = "no-back-button";
	window.location.hash = "Again-No-back-button";
	window.onhashchange = function() {
		window.location.hash = "no-back-button";
	}
</script>
<title>Welcome</title>
</head>

<body>
	<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="${pageContext.request.contextPath}/welcome"> <b>MTBC </b>
			</a>
		</div>
		<div>
			
			<ul class="nav navbar-nav navbar-right">
				<li>

					<form method="post"
						action="${pageContext.request.contextPath}/logout">

						<input class="btn btn-none" value="Logout" type="submit"
							style="background-color: #006f87; height: 50px; color: white;" />

						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>
				</li>
			</ul>
		</div>

	</div>
	</nav>


	<div class="container">
		<H1>Welcome to MTBC</H1>
		<h3>
			Hi
			<c:out value="${uname}"></c:out>
			!
		</h3>
		<div id="border" class="well">
			<center>
				<h4>What would you like to do??</h4>
				<br>

				<sec:authorize
					access="hasAnyRole('ROLE_NEW,ROLE_NEWMANAGER,ROLE_NEWEMPLOYEE,ROLE_NEWMERCHANT')">
					<a class="btn btn-info btn-large" style="width: 300px"
						href="${pageContext.request.contextPath}/userconfirm">Activate
						Your Account Here</a>
					<br />
					<br />
				</sec:authorize>
				<sec:authorize access="hasAnyRole('ROLE_USER,ROLE_MERCHANT')">
					<a class="btn btn-info btn-large" style="width: 300px"
						href="${pageContext.request.contextPath}/transactionhistory">View
						Transactions</a>
					<br />
					<br />
					<a class="btn btn-info btn-large" style="width: 300px"
						href="${pageContext.request.contextPath}/accountsummary">Account
						Summary</a>
					<br />
					<br />
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_EMPLOYEE')">
					<a class="btn btn-info btn-large" style="width: 300px"
						href="${pageContext.request.contextPath}/viewedituserdetails_employee"
						>View/Edit Customer Details</a>
					<br />
					<br />
					<a class="btn btn-info btn-large" style="width: 300px"
						href="${pageContext.request.contextPath}/bankers">Approve
						Transactions</a>
					<br />
					<br />
					<a class="btn btn-info btn-large" style="width: 300px"
						href="${pageContext.request.contextPath}/editemployeeprofile">Edit
						Profile</a>
					<br />
					<br />
				</sec:authorize>

				<sec:authorize access="hasRole('ROLE_MANAGER')">

					<a class="btn btn-info btn-large" style="width: 300px"
						href="${pageContext.request.contextPath}/usersignuprequest">Customer
						SignUp Request</a>
					<br />
					<br />
					<a class="btn btn-info btn-large" style="width: 300px"
						href="${pageContext.request.contextPath}/merchantsignuprequest">Merchant
						SignUp Request</a>
					<br />
					<br />
					<a class="btn btn-info btn-large" style="width: 300px"
						href="${pageContext.request.contextPath}/viewedituserdetails">View/Edit
						Customer Details</a>
					<br />
					<br />
					<a class="btn btn-info btn-large" style="width: 300px"
						href="${pageContext.request.contextPath}/deleteactiveusers">View/Edit
						Merchant Details</a>
					<br />
					<br />
					<a class="btn btn-info btn-large" style="width: 300px"
						href="${pageContext.request.contextPath}/approvetransaction">Approve
						Transaction</a>
					<br />
					<br />
					<a class="btn btn-info btn-large" style="width: 300px"
						href="${pageContext.request.contextPath}/editmanagerprofile">Edit
						Profile</a>
					<br />
					<br />
					</p>

				</sec:authorize>

				<sec:authorize access="hasRole('ROLE_USER')">
					<a class="btn btn-info btn-large" style="width: 300px"
						href="${pageContext.request.contextPath}/openTransaction">Transfer
						Funds</a>
					<br />
					<br />
					<a class="btn btn-info btn-large" style="width: 300px"
						href="${pageContext.request.contextPath}/openDebitCreditTransaction">Debit/Credit
						your account</a>
					<br />
					<br />
					<a class="btn btn-info btn-large" style="width: 300px"
						href="${pageContext.request.contextPath}/openMakePayment">Make
						Payment</a>
					<br />
					<br />
					<a class="btn btn-info btn-large" style="width: 300px"
						href="${pageContext.request.contextPath}/edituserprofile">Edit
						Profile</a>
					<br />
					<br />
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_MERCHANT')">
					<a class="btn btn-info btn-large" style="width: 300px"
						href="${pageContext.request.contextPath}/openMerchantTransaction">Transfer
						Funds</a>
					<br />
					<br />
					<a class="btn btn-info btn-large" style="width: 300px"
						href="${pageContext.request.contextPath}/editmerchantprofile">Edit
						Profile</a>
					<br />
					<br />
				</sec:authorize>
			</center>

		</div>
	</div>
	<div class="span4"></div>
	</div>
</body>
</html>
