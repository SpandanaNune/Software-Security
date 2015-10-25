<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<html>
<head>
<title>Login Page</title>
<script src="https://www.google.com/jsapi" type="text/javascript"></script>
    <script type="text/javascript">
    /**  How to setup a textarea that allows typing with a Hindi Virtual Keyboard. */
    
    google.load("elements", "1", {packages: "keyboard"});
    
    function onLoad() {
   //  var content = document.getElementById('content');
      // Create the HTML for out text area
    // content.innerHTML = '<textarea id="textCode" name="textCode" cols="100" rows="5"></textarea>';
    
      var kbd = new google.elements.keyboard.Keyboard(
          [google.elements.keyboard.LayoutCode.ENGLISH]
          );
    }
    
    function toggleVisible() {
    	  if (kbd.isVisible()) {
    	    kbd.setVisible(false);
    	    document.getElementById('btVisible').value = 'Show';
    	  } else {
    	    kbd.setVisible(true);
    	    document.getElementById('btVisible').value = 'Hide';
    	  }
    }
    
    google.setOnLoadCallback(onLoad);
    
    </script>
<link href="${pageContext.request.contextPath}/static/css/main.css"
	rel="stylesheet" type="text/css" />
</head>
<body onload='document.f.j_username.focus();'>
	<h3>Login with Username and Password</h3>
	<c:if test="${param.error != null}">
		<p class = "error">Login failed. Check your Login credentials.</p>
	</c:if>
	<form name='f' action="${pageContext.request.contextPath}/login"
		method="POST">
		<table id='content' class="formtable">
			<tr>
				<td>User:</td>
				<td><input type='text'  name='j_username' value=''></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='j_password' /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value="Login" /></td>
			</tr>


		</table>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
					<input type="button" id ='btVisible' onClick="toggleVisible()" value="Hide" />
			
	</form>
	<a href="${pageContext.request.contextPath}/forgotpass">Forgot
		Password</a>
</body>
</html>

