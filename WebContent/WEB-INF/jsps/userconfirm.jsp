<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Confirmation Page</title>

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
				$("#matchpass").text("paasswords do not match");
				$("#matchpass").addClass("error");
				$("#matchpass").removeClass("valid");

			}
		}

	}
	$(document).ready(onLoad);
</script>
>
<meta content="Content-Type" content="text/html; charset-US-ASCII">
</head>
<body>
	Activate User Account
	<br />

	<sf:form id="details" method="post"
		action="${pageContext.request.contextPath}/activateuser" commandName = "users">

		<table class="formtable">
			<%-- <tr>
				<td class="label">UserName:</td>
				<td><sf:input class="control" path="username" name="username"
						type="text" /><br /> <sf:errors path="username" Class="error"></sf:errors></td>
			</tr>
 --%>
			<tr>
				<td class="label">New Password:</td>
				<td><sf:input id="password" class="control" path="password"
						name="password" type="text" /><br /> <sf:errors path="password"
						Class="error"></sf:errors></td>
			</tr>
			<tr>
				<td class="label">Confirm Password:</td>
				<td><input id="confirmpassword" class="control"
					name="confirmpassword" type="text" />
				<div id="matchpass"></div></td>
			</tr>
			<tr>
				<td class="label">Q1:What is your mother's maiden name:</td>
				<td><sf:input class="control" path="q1" name="q1"
						type="text" /><br /> <sf:errors path="q1" Class="error"></sf:errors></td>
			</tr>
			<tr>
				<td class="label">Q2:Name of your Highschool:</td>
				<td><sf:input class="control" path="q2" name="q2"
						type="text" /><br /> <sf:errors path="q2" Class="error"></sf:errors></td>
			</tr>
			<tr>
				<td class="label">Q3:Name your favourite colour:</td>
				<td><sf:input class="control" path="q3" name="q3"
						type="text" /><br /> <sf:errors path="q3" Class="error"></sf:errors></td>
			</tr>
			<tr>
				<td class="label"></td>
				<td><input class="control" value="Activate" type="submit" /></td>
			</tr>
		</table>

	</sf:form>

</body>
</html>