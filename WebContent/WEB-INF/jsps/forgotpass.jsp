<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
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
  <script>
function validateForm() {
   
    var email=".+@.+\\..+";
   
    
    var x3 = document.forms["myForm"]["email"].value;
    if (x3.match(email) && x3.length>=4 && x3.length<=45) {
	   $("#matchpass3").text("");
	$("#matchpass3").addClass("valid");
	$("#matchpass3").removeClass("error");
    }
    
   else
   {
   $("#matchpass3").addClass("error");
	$("#matchpass3").text("invalid email id");
	$("#matchpass3").removeClass("valid");
	return false;
	//f=1;
   }
    document.getElementById("button").disabled=true;
}
   </script>
  <title>Forgot Password</title>
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
  <div class="container well">
  <div class="alert alert-info">
   <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
   <strong>Info!</strong> You will receive an email after you proceed. 
 </div>
    <center>

	</div>
	</nav>
	<div class="container well">
		<center>
      <br />
      <h1> Forgot Password </h1>
      <br>
      <br>
      <sf:form method="post" name="myForm" onsubmit="return validateForm()" 
      action="${pageContext.request.contextPath}/forgotPassEmailSuccess" htmlEscape="true" >

        <table>
          <tr>
            <td class="label">
              <h4 style="color:black">Enter Email:</h4>
            </td>
            <td>&nbsp;</td>
            <td>
              <input class="control form-control" name="email" type="text" />
              <div id="matchpass3"></div>
              <br />
            </td>
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
						<td class="label">
							<h4 style="color: black">Verify Captcha:</h4>
						</td>
						<td>&nbsp;</td>
						<td>
							<div class="g-recaptcha"
								data-sitekey="6LcHCw8TAAAAAIqGUaZBHaZbJ4ra61tME5Lz3zB7"></div>
						</td>
					</tr>
				</table>
				<br>
				<br>
				<br>
				<input id="button" class="control btn btn-info" value="Send Link" type="submit" />
				<br>
      </sf:form>
      <br>
      <br>
      <a href="${pageContext.request.contextPath}/welcome">
        <span class="glyphicon glyphicon-log-in"></span>&nbsp;Login</a>
    </center>
  </div>
</body>

</html>
