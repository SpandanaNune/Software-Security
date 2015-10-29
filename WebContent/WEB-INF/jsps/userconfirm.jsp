<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Confirmation Page</title>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='https://www.google.com/recaptcha/api.js'></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="navbar.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<noscript>
	<h2>JavaScript is disabled! Why you want to do so? Please enable
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

<link href="${pageContext.request.contextPath}/static/css/main.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/script/jquery.js"></script>
<script type="text/javascript">
	function onLoad() {
		$("#password").keyup(checkPasswordMatch);
		$("#confirmpassword").keyup(checkPasswordMatch);
		$("$details").submit(canSubmit);
	}

	function canSubmit() {

		var password = $("#password").val();
		var confirmpassword = $("#confirmpassword").val();
		if (password != confirmpassword) {
			alert("Password do not match!");
			return false;
		} else {
			return true;
		}
	}

	function checkPasswordMatch() {
		var password = $("#password").val();
		var confirmpassword = $("#confirmpassword").val();
		if (password.length > 3 || confirmpassword.length > 3) {

			if (password == confirmpassword) {
				$("#matchpass").text("paasswords match");
				$("#matchpass").addClass("valid");
				$("#matchpass").removeClass("error");

			} else {
				$("#matchpass").text("Password and Confirm password does not match");
				$("#matchpass").addClass("error");
				$("#matchpass").removeClass("valid");

			}
		}

	}
	$(document).ready(onLoad);
</script>
<meta content="Content-Type" content="text/html; charset-US-ASCII">
</head>

<body>
	<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/">
				<b>MTBC </b>
			</a>
		</div>
		<div>
			<ul class="nav navbar-nav">
				<li><a href="#">About Us</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="${pageContext.request.contextPath}/login"> <span
						class="glyphicon glyphicon-log-in"></span> Login
				</a></li>
			</ul>
		</div>

	</div>
	</nav>
	<div class="container well">
		<center>
			<h1>Activate User Account</h1>
			<br />

			<sf:form id="details" method="post"
				action="${pageContext.request.contextPath}/activateuser"
				commandName="users">
				<table>

					<tr>
						<td>User Name:</td>		
						<td><sf:input class="form-control" path="username"
								name="username" type="text" readonly="true" value="${uname}" /><sf:errors
								path="username" Class="error" ></sf:errors></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>New Password:</td>
						<td><sf:input id="password" class="form-control"
								path="password" name="password" type="password" /> <sf:errors
								path="password" Class="error"></sf:errors></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>Confirm Password:</td>
						<td><input id="confirmpassword" class="form-control"
							name="confirmpassword" type="password" />
							<div id="matchpass"></div></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>Q1:What is your mother's maiden name:</td>
						<td><sf:input class="form-control" path="q1" name="q1"
								type="text" /><sf:errors path="q1" Class="error"></sf:errors>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>Q2:Name of your Highschool:</td>
						<td><sf:input class="form-control" path="q2" name="q2"
								type="text" /><sf:errors path="q2" Class="error"></sf:errors>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>Q3:Name your favourite colour:</td>
						<td><sf:input class="form-control" path="q3" name="q3"
								type="text" /><sf:errors path="q3" Class="error"></sf:errors>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>
				<br>
				<br>
				<input class="btn btn-info" value="Activate" type="submit" />


				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</sf:form>
	</div>
	</center>
</body>

</html>