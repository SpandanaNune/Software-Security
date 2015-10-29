<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src='https://www.google.com/recaptcha/api.js'></script>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <link rel="stylesheet" href="navbar.css">
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
  <title>Success</title>
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
  </nav>
  <br>
  <br>
  <center>
    <div class="container well">
      <br>
      <h1>
        <span class="label label-success">Success!</span>
      </h1>
      <br>
      <br> Password Reset Successful
      <br/>
      <br/>
      <a href="${pageContext.request.contextPath}/welcome">
        <span class="glyphicon glyphicon-log-in"></span>&nbsp;Login</a>
</body>

</html>
    