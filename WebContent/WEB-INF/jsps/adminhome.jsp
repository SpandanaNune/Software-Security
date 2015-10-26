<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Admin Home</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>Admin Home</title>
</head>
<body>
	<div class="container">
		<h1>Admin Home</h1>
		<h3>
			Hi
			<c:out value="${user.getUsername()}"></c:out>
			!
		</h3>
		<div id="border" style="border: 2px solid black; border-radius: 25px;">
			<center>
				<h4>What would you like to do??</h4>
				<br> <a class="btn btn-info btn-large"
					href="${pageContext.request.contextPath}/employeecreation">Add
					Employee</a>
</body>
<br>
<br>
<br>
<a class="btn btn-info btn-large"
	href="${pageContext.request.contextPath}/getinternalusers">View/Edit
	Employee</a>
<br>
<br>
<br>
<a class="btn btn-info btn-large"
	href="${pageContext.request.contextPath}/transactionlog">Transaction
	Logs</a>
</body>
<br>
<br>
<br>
<a class="btn btn-info btn-large"
	href="${pageContext.request.contextPath}/pii">Approve PII</a>
<br>
<br>
<br>
<a class="btn btn-info btn-large"
	href="${pageContext.request.contextPath}/editadminprofile">Edit
	Profile</a>

</body>
<br>
<br>
</center>
</div>
</div>
</html>