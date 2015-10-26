<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language="javascript">
	document.onmousedown = disableclick;
	status = "Right Click Is Disabled For The Website";
	function disableclick(event) {
		if (event.button == 2) {
			alert(status);
			return false;
		}
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/static/css/main.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>Merchant Home</title>
</head>
<body>
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
					access="hasRole('ROLE_NEW,ROLE_NEWMANAGER,ROLE_NEWEMPLOYEE,ROLE_NEWMERCHANT')">
					<a class="btn btn-info btn-large"
						href="${pageContext.request.contextPath}/userconfirm">Activate
						Your Account Here</a>
					<br />
					<br />
				</sec:authorize>

				<sec:authorize access="hasRole('ROLE_EMPLOYEE')">
					<a class="btn btn-info btn-large"
						href="${pageContext.request.contextPath}/viewedituserdetails_employee">View/Edit
						Customer Details</a>
					<br />
					<br />
					<a class="btn btn-info btn-large"
						href="${pageContext.request.contextPath}/bankers">View
						Transactions</a>
					<br />
					<br />
					<a class="btn btn-info btn-large"
						href="${pageContext.request.contextPath}/editemployeeprofile">Edit
						Profile</a>
					<br />
					<br />
				</sec:authorize>

				<sec:authorize access="hasRole('ROLE_MANAGER')">

					<a class="btn btn-info btn-large"
						href="${pageContext.request.contextPath}/usersignuprequest">User SignUp Request</a>
					<br />
					<br />
					<a class="btn btn-info btn-large"
						href="${pageContext.request.contextPath}/viewedituserdetails">View/Edit Customer Details</a>
					<br />
					<br />
					<a class="btn btn-info btn-large"
						href="${pageContext.request.contextPath}/deleteactiveusers">Block Active Customer</a>
					<br />
					<br />
					<a class="btn btn-info btn-large"
						href="${pageContext.request.contextPath}/approvetransaction">View Transaction</a>
					<br />
					<br />
					<a class="btn btn-info btn-large"
						href="${pageContext.request.contextPath}/editmanagerprofile">Edit Profile</a><br /> <br />
					</p>

				</sec:authorize>

				<form method="post"
					action="${pageContext.request.contextPath}/logout">
					<input class="control btn btn-info" value="Logout" type="submit" />
					<input type="hidden" name="${_csrf.parameterName}"
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