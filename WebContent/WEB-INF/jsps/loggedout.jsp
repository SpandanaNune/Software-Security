<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="https://www.google.com/jsapi" type="text/javascript"></script>
<title>Logout</title>
</head>
<body>
  <br><br>
  <center>
​
  <div class="container well">
    <br><br>
    <img src="${pageContext.request.contextPath}/static/images/mtbclogo.png"></img>
    <h1><span class="label label-success"><span class="glyphicon glyphicon-log-out"></span>&nbsp;Logged Out</span></h1>
    <br><br>
You have been successfully logged out. Please log in again from the home page.
<br/><br/>
<a href="${pageContext.request.contextPath}/"><span class="glyphicon glyphicon-home"></span>&nbsp;Home</a>
</div>
</center>
</body>
</html>