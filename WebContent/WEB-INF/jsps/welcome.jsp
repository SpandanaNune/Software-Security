<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
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
					<sec:authorize access="hasRole('ROLE_NEW')">
						<a class="btn btn-primary btn-large"
							href="${pageContext.request.contextPath}/userconfirm">UserConfirmation</a>
					</sec:authorize>

					<sec:authorize access="hasRole('ROLE_USER')">
						<a class="btn btn-primary btn-large"
							href="${pageContext.request.contextPath}/createTransaction">Make
							transactions</a>
						<a class="btn btn-primary btn-large"
							href="${pageContext.request.contextPath}/getAccountDetails">Get
							Account Details</a>
					</sec:authorize>
					<a class="btn btn-primary btn-large"
						href="${pageContext.request.contextPath}/logout">Logout</a>
						
						<form method="post"
						action="${pageContext.request.contextPath}/logout">
						<input class="control btn btn-info" value = "Logout" type="submit" /> <input
							type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>
			</div>
		</div>
		<div class="span4"></div>
	</div>



	<%-- <a href = "${pageContext.request.contextPath}/viewuser">Click Here to Login</a>
 --%>

</body>
</html>