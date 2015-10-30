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
  <title>View/Edit User Details</title>
</head>

<body>
  <nav class="navbar navbar-default">
    <div class="container-fluid">
      <div class="navbar-header">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/welcome">
          <b>MTBC </b>
        </a>
      </div>
      <div>
       
        <ul class="nav navbar-nav navbar-right">
          <li>
            <form method="post"
						action="${pageContext.request.contextPath}/logout">

						<input class="btn btn-none" value="Logout" type="submit"
							style="background-color: #006f87; height: 50px; color: white;" />

						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>

          </li>
        </ul>
      </div>

    </div>
  </nav>
  <div class="container">
    <h1>View / Edit User Details</h1>
    <table class="table table-hover">
      <tr>
        <th>UserName</th>
        <th>FirstName</th>
        <th>LastName</th>
        <th>Email</th>
        <th>Action</th>
        <th></th>
      </tr>
      ​
      <c:forEach var="user" items="${user}">
        <tr>
          <td>
            <c:out value="${user.getUsername()}"></c:out>
          </td> ​
          <td>
            <c:out value="${user.getFirstname()}"></c:out>
          </td> ​
          <td>
            <c:out value="${user.getLastname()}"></c:out>
          </td> ​
          <td>
            <c:out value="${user.getEmail()}"></c:out>
          </td>
          <td>
            <form method="post" action="${pageContext.request.contextPath}/editbtn">
              <input type="hidden" name="View/Edit" value="${user.getUsername()}" />
              <input class="control btn btn-info" value="View/Edit" type="submit" />
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </form>
          </td>
          <td>
            <form method="post" action="${pageContext.request.contextPath}/deleteuser">
              <input type="hidden" name="Delete" value="${user.getUsername()}" />
              <input class="control btn btn-danger" value="Delete" type="submit"  onClick ="this.form.submit(); this.disabled=true;"/>
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </form>
          </td>


        </tr>
      </c:forEach>
    </table>
  </div>
</body>
