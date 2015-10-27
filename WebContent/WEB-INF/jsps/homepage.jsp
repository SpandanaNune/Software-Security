<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
	<c:out value="${user.getUsername()}"></c:out>

	<div class="row-fluid">
		<div class="span4"></div>
		<div class="span4">
			<div class="hero-unit">
				<h1>Hi There</h1>

				<p>
					<a class="btn btn-primary btn-large"
						href="${pageContext.request.contextPath}/registeruser">Register

						here</a>
						<a class="btn btn-primary btn-large"
						href="${pageContext.request.contextPath}/mylogin">LoginAgain</a>
							 <a class="btn btn-primary btn-large"
						href="${pageContext.request.contextPath}/sendOTP">Send OTP [Arjun]</a>

							 <a class="btn btn-primary btn-large"
						href="${pageContext.request.contextPath}/verifyOTP">Verify OTP [Arjun]</a>
						
						<a class="btn btn-primary btn-large"
						href="${pageContext.request.contextPath}/merchanthome">Merchant Home</a>
						<a class="btn btn-primary btn-large"
						href="${pageContext.request.contextPath}/managerhome">Manager Home</a>
						
						<a class="btn btn-primary btn-large"
						href="${pageContext.request.contextPath}/merchant">Merchant Register</a>
						
						<a class="btn btn-primary btn-large"
						href="${pageContext.request.contextPath}/signup">LoginAgain</a>
						
						<a class="btn btn-primary btn-large"
						href="${pageContext.request.contextPath}/transactionhistory">Show transactions</a>
						
						<a class="btn btn-primary btn-large"
						href="${pageContext.request.contextPath}/welcome">welcome</a>
						
						<a class="btn btn-primary btn-large"
						href="${pageContext.request.contextPath}/userconfirm">UserConfirmation</a>
						
						
						<a class="btn btn-primary btn-large"
						href="${pageContext.request.contextPath}/openTransaction">Open transactions [Arjun]</a>
						
						<a class="btn btn-primary btn-large"
						href="${pageContext.request.contextPath}/openDebitCreditTransaction">Debit credit your account [Arjun]</a>
						
						<a class="btn btn-primary btn-large"
						href="${pageContext.request.contextPath}/openMakePayment">MakePayment [Arjun]</a>
						
						<a class="btn btn-primary btn-large"
						href="${pageContext.request.contextPath}/random">Random </a>
						
						<a class="btn btn-primary btn-large"
						href="${pageContext.request.contextPath}/Sample">Random </a>
						<a class="btn btn-primary btn-large"
						href="${pageContext.request.contextPath}/accountactivation">Verify Account</a>
						<a class="btn btn-primary btn-large"
						href="${pageContext.request.contextPath}/edituserprofile">Edit Customer Profile</a>
						<a class="btn btn-primary btn-large"
						href="${pageContext.request.contextPath}/editmerchantprofile">Edit Merchant Profile</a>
						<a href ="<c:url value ='/logout'/>">Log out</a>
				</p>
				

			</div>
		</div>
		<div class="span4"></div>
	</div>

		<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />

	<%-- <a href = "${pageContext.request.contextPath}/viewuser">Click Here to Login</a>
 --%>

</body>
</html>