<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Welcome!</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<link href="${pageContext.request.contextPath}/static/css/navbar.css"
	rel="stylesheet" type="text/css" />
​
</head>
<body>

	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#"> <b>MTBC </b>
				</a>
			</div>
			<div>
				<ul class="nav navbar-nav">
					<li><a href="#">About Us</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#"><span
							class="glyphicon glyphicon-user"></span> Sign Up <span
							class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a
								href="${pageContext.request.contextPath}/registeruser">As
									Customer</a></li>
							<li><a href="${pageContext.request.contextPath}/merchant">As
									Merchant</a></li>
						</ul></li>
					<li><a href="${pageContext.request.contextPath}/welcome"><span
							class="glyphicon glyphicon-log-in"></span> Login</a></li>
				</ul>
			</div>
			​
		</div>
	</nav>
	​
	<div class="container well well-lg">
		<center>
			<h1 style="color: #006f87">Welcome to Money Tree Banking Corp ©</h1>
			​
			<h4>
				<i>A smarter way to bank!</i>
			</H4>
			<br>
			<img
				src="${pageContext.request.contextPath}/static/images/mtbclogo.png"></img>
			​ <br> <br>
			<p>At MTBC, we understand your banking needs.</p>
			<br> <br>
			<button type="submit" class="btn btn-info">
				<span class="glyphicon glyphicon-log-in"></span>&nbsp;Login
			</button>
		</center>
	</div>


</body>