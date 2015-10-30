<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src='https://www.google.com/recaptcha/api.js'></script>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<title>Reset Password Page</title>


	<script type="text/javascript" src="${pageContext.request.contextPath}/static/script/jquery.js"></script>

	<noscript>
		<h2>JavaScript is disabled! Please enable JavaScript in your web browser!</h2>
		<style type="text/css">
			#main-content {
				display: none;
			}
		</style>
	</noscript>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/navbar.css">
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
				$("#matchpass").text("Passwords match");
				$("#matchpass").addClass("valid");
				$("#matchpass").removeClass("error");

			} else {
					$("#matchpass").text("passwords do not match");
					$("#matchpass").addClass("error");
					$("#matchpass").removeClass("valid");

				}
			}

		}
		$(document).ready(onLoad);
	</script>
	<script>
function validateForm() {
   
	var alphanum=/^[0-9a-zA-Z]+$/;
	var alphanums=/^[0-9a-zA-Z\s]+$/;
    var pass=/^[0-9a-zA-Z_@]+$/;
	var x3 = document.forms["details"]["q1"].value;
    
	   if (x3.match(alphanums) && x3.length>=1 && x3.length<=50) {
		   $("#matchpass3").text("");
		$("#matchpass3").addClass("valid");
		$("#matchpass3").removeClass("error");
	    }
	    
	   else
	   {
	   $("#matchpass3").addClass("error");
		$("#matchpass3").text("answer must be alphanumeric and between 8 and 45 characters");
		$("#matchpass3").removeClass("valid")
		return false;
		//f=1;
	   }
	   
	   var x4 = document.forms["details"]["q2"].value;
	    
	   if (x4.match(alphanums) && x4.length>=1 && x4.length<=50) {
		   $("#matchpass4").text("");
		$("#matchpass4").addClass("valid");
		$("#matchpass4").removeClass("error");
	    }
	    
	   else
	   {
	   $("#matchpass4").addClass("error");
		$("#matchpass4").text("answer must be alphanumeric and between 8 and 45 characters");
		$("#matchpass4").removeClass("valid")
		return false;
		//f=1;
	   }
	   
	   var x5 = document.forms["details"]["q3"].value;
	    
	   if (x5.match(alphanums) && x5.length>=1 && x5.length<=50) {
		   $("#matchpass5").text("");
		$("#matchpass5").addClass("valid");
		$("#matchpass5").removeClass("error");
	    }
	    
	   else
	   {
	   $("#matchpass5").addClass("error");
		$("#matchpass5").text("answer must be alphanumeric and between 8 and 45 characters");
		$("#matchpass5").removeClass("valid")
		return false;
		//f=1;
	   }
	   
	   var x1 = document.forms["details"]["password"].value;
	   if (x1.match(pass) && x1.length>=5 && x1.length<=100) {
		   $("#matchpass1").text("");
		$("#matchpass1").addClass("valid");
		$("#matchpass1").removeClass("error");
	    }
	    
	   else
	   {
	   $("#matchpass1").addClass("error");
		$("#matchpass1").text("password can contain only alphabets, digits and special characters _ @ ");
		$("#matchpass1").removeClass("valid")
		return false;
		//f=1;
	   }
	   var x2 = document.forms["details"]["confirmpassword"].value;
	   if (x2.match(pass) && x2.length>=5 && x2.length<=100) {
		   $("#matchpass2").text("");
		$("#matchpass2").addClass("valid");
		$("#matchpass2").removeClass("error");
	    }
	    
	   else
	   {
	   $("#matchpass2").addClass("error");
		$("#matchpass2").text("password can contain only alphabets, digits and special characters _ @ ");
		$("#matchpass2").removeClass("valid")
		return false;
		//f=1;
	   }

}
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
				
				<ul class="nav navbar-nav navbar-right">
					<li>
						<a href="${pageContext.request.contextPath}/welcome">
							<span class="glyphicon glyphicon-log-in"></span> Login</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	</nav>
	<div class="container well">
		<center>
			<h1>Reset My Password</h1>
			<br />

			<sf:form name='details' onsubmit="return validateForm()"
			id="details" method="post" action="${pageContext.request.contextPath}/resetpasswordbtn" commandName="users" htmlEscape="true" >
				<c:if test="${param.error != null}">
					<p class="error">Please enter valid inputs in all fields</p>
				</c:if>
				<table>
					<tr>
						<td class="label">
							<h4 style="color: black">Username:</h4>
						</td>
						<td>&nbsp;</td>
						<td>
							<sf:input class="control" path="username" name="username" type="text" readonly ="true" />
							<br />
							<sf:errors path="username" Class="error"></sf:errors>
						</td>
					</tr>
					<tr>
						<td class="label">
							<h4 style="color: black">New Password:</h4>
						</td>
						<td>&nbsp;</td>
						<td>
							<input id="password" class="control form-control" name="password" type="password" />
							<div id="matchpass1"></div>
							<br />
					</tr>
					<tr>
						<td class="label">
							<h4 style="color: black">Confirm Password:</h4>
						</td>
						<td>&nbsp;</td>
						<td>
							<input id="confirmpassword" class="control form-control" name="confirmpassword" type="password" />
							<div id="matchpass"></div>
							<div id="matchpass2"></div>
						</td>
					</tr>
					<tr>
						<td class="label">
							<h4 style="color: black">Q1:What is your mother's maiden name:</h4>
						<div id="matchpass3"></div></td>
						<td>&nbsp;</td>
						<td><input class="control form-control" name="q1" type="text" />
							<br />
					</tr>
					<tr>
						<td class="label">
							<h4 style="color: black">Q2:Name of your Highschool:</h4>
						<div id="matchpass4"></div></td>
						<td>&nbsp;</td>
						<td><input class="control form-control" name="q2" type="text" />
							<br />
					</tr>
					<tr>
						<td class="label">
							<h4 style="color: black">Q3:Name your favourite colour:</h4>
						<div id="matchpass5"></div></td>
						<td>&nbsp;</td>
						<td><input class="control form-control" name="q3" type="text" />
							<br />
					</tr>
				</table>
				<br>
				<br>
				<input class="control btn btn-info" value="Reset Password"
					type="submit" />

				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</sf:form>
	</div>
	</center>
</body>

</html>
