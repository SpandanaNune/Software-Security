<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Reset Password</title>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="https://www.google.com/jsapi" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/static/css/main.css"
	rel="stylesheet" type="text/css" />
</head>
<body>
  <center>
    <br><br>
  <div class="container">
	<h1>Reset Password</h1>
	<br />

	<sf:form method="post"
		action="${pageContext.request.contextPath}/resetpasswordbtn"
		commandName="users">

		<table class="formtable">
			<tr>
				<td class="label"><h4 style="color:black">New Password:</h4></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
				<td><sf:input class="control form-control" path="password" name="password"
						type="text" value=""/><br /> <sf:errors path="password" Class="error"></sf:errors></td>
			</tr>
			<tr>
				<td class="label"><h4 style="color:black">Confirm Password:</h4></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
				<td><input class="control form-control"  name="confirmpassword"
						type="text" /><br /></td>
			</tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
			</table>

				<input class="control btn btn-info" value="Register" type="submit" />



	</sf:form>
</div>
</center>
</body>
</html>
