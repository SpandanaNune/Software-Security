<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script>
	$(document).ready(function() {
		$('.btn').click(function Ajax1() {
			var data= 
			$.ajax({
				url : '${pageContext.request.contextPath}/one',
				success : function(data) {
					$('#one').html(data);
				}
			});
		});
		$('#data3').click(function Ajax2() {
			$.ajax({
				url : '${pageContext.request.contextPath}/two',
				success : function(data) {
					$('#two').html(data);
				}
			});
		});
		$('#data2').click(function Ajax3() {
			$.ajax({
				url : '${pageContext.request.contextPath}/three',
				success : function(data) {
					$('#three').html(data);
				}
			});
		});
	})
</script>
</head>
<body>

<div class="navbar">
  <div class="navbar-inner">
    <div class="container">
 
      <!-- .btn-navbar is used as the toggle for collapsed navbar content -->
      <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </a>
 
      <!-- Be sure to leave the brand out there if you want it shown -->
      <a class="brand" href="#">Project name</a>
  <a class="brand" href="#">Project name1</a>
   <a class="brand" href="#">Project name2</a>
    <a class="brand" href="#">Project name3</a>
     <a class="brand" href="#">Project name4</a>
      <!-- Everything you want hidden at 940px or less, place within here -->
      <div class="nav-collapse collapse">
        <!-- .nav, .navbar-search, .navbar-form, etc -->
      </div>
 
    </div>
  </div>
</div>
	<!-- Hi there! -->
	<c:out value="${name}"></c:out>

	<!-- <h2>Results</h2> -->

	<c:forEach var="user" items="${offers}">
	    Userid ${user.userid}<br/>
	    Username ${user.username}<br/>
	</c:forEach> 
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="jumbotron">
					<h2>Hello, world!</h2>
					<p>This is a template for a simple marketing or informational
						website. It includes a large callout called the hero unit and
						three supporting pieces of content. Use it as a starting point to
						create something more unique.</p>
					<p>
						<a class="btn btn-primary btn-large" href="#">Learn more</a>
					</p>
				</div>
				<div class="row">
					<div class="col-md-4">
						<a class="btn btn-primary btn-large" href="#" id="data1">Data
							1</a>
						<div id="one"></div>
					</div>
					<div class="col-md-4">
						<a class="btn btn-primary btn-large" href="#" id="data2">Data
							2</a>
						<div id="two"></div>
					</div>
					<div class="col-md-4">
						<a class="btn btn-primary btn-large" href="#" id="data3">Data
							3</a>
						<div id="three"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>