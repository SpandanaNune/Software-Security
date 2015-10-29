<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <title>Login</title>
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <link rel="stylesheet" href="navbar.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <script src="https://www.google.com/jsapi" type="text/javascript"></script>

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

  <script type="text/javascript">
    /**  How to setup a textarea that allows typing with a Hindi Virtual Keyboard. */

    google.load("elements", "1", {
      packages: "keyboard"
    });

    function onLoad() {
      //  var content = document.getElementById('content');
      // Create the HTML for out text area
      // content.innerHTML = '<textarea id="textCode" name="textCode" cols="100" rows="5"></textarea>';

      var kbd = new google.elements.keyboard.Keyboard(
        [google.elements.keyboard.LayoutCode.ENGLISH]);
    }

    google.setOnLoadCallback(onLoad);
  </script>
  <link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet" type="text/css" />
</head>

<body onload='document.f.j_username.focus();'>
  <br>
  <br>
  <div class="container well">
    <center>
      <br>
      <br>
      <img src="${pageContext.request.contextPath}/static/images/mtbclogo.png"></img>
      <h3>Login</h3>
      <c:if test="${param.error != null}">
        <p class="error">Login failed. Check your Login credentials.</p>
      </c:if>
      <br>
      <form name='f' action="${pageContext.request.contextPath}/login" method="POST">
        <table id='content'>
          <tr>
            <td>User:</td>
            <td>&nbsp;</td>
            <td>
              <input type='text' name='j_username' value='' class="form-control">
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>Password:</td>
            <td>&nbsp;</td>
            <td>
              <input type='password' name='j_password' class="form-control" />
            </td>
          </tr>

        </table>
        <br>
        <input name="submit" type="submit" value="Login" class="btn btn-info" />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      </form>
      <a href="${pageContext.request.contextPath}/forgotpass">Forgot
				Password</a>
    </center>
  </div>
</body>

</html>
