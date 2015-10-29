<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src='https://www.google.com/recaptcha/api.js'></script>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

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
  <title>Welcome to MTBC!</title>
</head>

<body>

  <nav class="navbar navbar-default">
    <div class="container-fluid">
      <div class="navbar-header">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/"> <b>MTBC </b>
        </a>
      </div>
      <div>
        <ul class="nav navbar-nav">
          <li><a href="${pageContext.request.contextPath}/about">About Us</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
              <span class="glyphicon glyphicon-user"></span> Sign Up
              <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
              <li><a href="javascript:window.open('${pageContext.request.contextPath}/registeruser','winname','directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=1000,height=600');">As
									Customer</a></li>
              <li><a href="javascript:window.open('${pageContext.request.contextPath}/merchant','winname','directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=1000,height=600');">As
									Merchant</a></li>
            </ul>
          </li>
          <li>
            <a href="javascript:window.open('${pageContext.request.contextPath}/welcome','winname','directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=1000,height=600');">
              <span class="glyphicon glyphicon-log-in"></span> Login</a>
          </li>
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
      <img src="${pageContext.request.contextPath}/static/images/mtbclogo.png"></img>
      ​
      <br>
      <br>
      <p>At MTBC, we understand your banking needs.</p>
      <br>
      <br>
     <a class="btn btn-info" href="javascript:window.open('${pageContext.request.contextPath}/welcome','winname','directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=1000,height=600');">
             <span class="glyphicon glyphicon-log-in"></span> Login</a>
    </center>
  </div>


</body>

</html>