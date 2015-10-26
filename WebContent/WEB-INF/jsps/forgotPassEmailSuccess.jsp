<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="https://www.google.com/jsapi" type="text/javascript"></script>
<title>Success</title>
</head>
<body>
  <br><br>
  <center>
  <div class="container well">
    <br>
    <h1><span class="label label-success">Success!</span></h1>
    <br><br>
An Email has been sent to your account please click on the link to reset your password.
<br/><br/>
<a href="${pageContext.request.contextPath}/login"><span class="glyphicon glyphicon-log-in"></span>&nbsp;Login</a>
</div>
</center>
</body>
</html>
