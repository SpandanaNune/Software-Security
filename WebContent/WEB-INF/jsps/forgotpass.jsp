<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forgot Password</title>
<script src='https://www.google.com/recaptcha/api.js'></script>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="https://www.google.com/jsapi" type="text/javascript"></script>
</head>
<body>
  <div class="container">
    <center>
	<br />
  <h1> Forgot Password </h1><br><br>
	<sf:form method="post"
		action="${pageContext.request.contextPath}/forgotPassEmailSuccess">

		<table >
			<tr>
				<td class="label"><h4 style="color:black">Enter Email:</h4></td>
        <td>&nbsp;</td>
				<td><input class="control form-control"  name="email"
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
			<tr>
				<td class="label"><h4 style="color:black">Verify Captcha:</h4></td>
        <td>&nbsp;</td>
				<td><div class="g-recaptcha" data-sitekey="6LcHCw8TAAAAAIqGUaZBHaZbJ4ra61tME5Lz3zB7"></div></td>
			</tr>
        </table>
        <br><br><br>
				<input class="control btn btn-info" value="Register" type="submit" />
        <br>


	</sf:form>
  <br><br>
	<a href="${pageContext.request.contextPath}/login"><span class="glyphicon glyphicon-log-in"></span>&nbsp;Login</a>
  </center>
</div>
</body>
</html>
