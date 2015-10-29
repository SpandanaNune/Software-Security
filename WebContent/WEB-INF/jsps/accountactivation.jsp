<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <link rel="stylesheet" href="navbar.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

  <noscript>
    <h2>JavaScript is disabled! Why you want to do so? Please enable JavaScript in your web browser!</h2>
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
  <title>Account Activation</title>
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
        <ul class="nav navbar-nav">
          <li><a href="#">About Us</a></li>
        </ul>

      </div>

    </div>
  </nav>
  <div class="container well">
    <center>
      <h1>Account Activation</h1>
      <br>
      <br>
      <sf:form method="post" action="${pageContext.request.contextPath}/accountactivation" commandName="users" htmlEscape="true" >

        <table>
          <tr>
            <td class="label"><h4 style="color: black">UserName:&nbsp;</td>
            <td>
              <sf:input class="control form-control" path="username" name="username" type="text" />
              <br />
              <sf:errors path="username" Class="error"></sf:errors>
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td class="label"><h4 style="color: black">New Password:&nbsp;</td>
            <td>
              <sf:input class="control form-control" path="password" name="password" type="password" />
              <br />
              <sf:errors path="password" Class="error"></sf:errors>
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td class="label"><h4 style="color: black">Confirm Password:&nbsp;</td>
            <td>
              <input class="control form-control" name="confirmpassword" type="password" />
              <br />
            </td>
          </tr>
        </table>
        <br><br>
        <input class="control btn btn-info" value="SetUp" type="submit" />



      </sf:form>
    </center>
  </div>
</body>

</html>
