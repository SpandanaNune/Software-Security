<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

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
  <script>
function validateForm() {
    var alphanum=/^[0-9a-zA-Z]+$/;
    var x = document.forms["myForm"]["otpValue"].value;
    
    if (x.match(alphanum) && x.length>=1 && x.length<=45) {
 	   $("#matchpass").text("");
 	$("#matchpass").addClass("valid");
 	$("#matchpass").removeClass("error");
     }
     
    else
    {
    $("#matchpass").addClass("error");
 	$("#matchpass").text("OTP value should not be empty");
 	$("#matchpass").removeClass("valid")
 	return false;
 	//f=1;
    }
    document.getElementById("button").disabled=true;
}
</script>

  <title>Complete Registration</title>
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
   <strong>Info!</strong> After verifying your account, a request will be sent to the bank for approval. You will receive an email after approval
 </div>
    <br>
    <br>
    <center>
      <h1>Confirm Registration</h1>
      <br />
      <sf:form name="myForm" onsubmit="return validateForm()" 
      method="post" action="${pageContext.request.contextPath}/registerbtn2" htmlEscape="true" >
        <table>
          <tr>
            <td>
              <input class="control" name="mail" value="${mail}" type="hidden" />
            </td>
          </tr>

          <tr>
            <td class="label">
              <h4 style="color: black">Enter OTP:</h4>
            </td>
            <td>
              &nbsp;
            </td>
            <td>
              <input class="control form-control" name="otpValue" type="text" />
              <div id="matchpass"></div>
            </td>
          </tr>
        </table>
        <br>
        <br>
        <input id="button" class="control btn btn-info" value="Verify" type="submit" />


        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      </sf:form>
      <center>
  </div>
</body>

</html>
