
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
  <title>Merchant Transactions</title>
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
        <ul class="nav navbar-nav">
          <li><a href="#">About Us</a></li>
        </ul>
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
    <sf:form method="post" action="${pageContext.request.contextPath}/createMerchnatTransaction" commandName="transactionDetails" htmlEscape="true">


      <h1>Transfer Funds</h1>
      <h3>
        Hi
        <c:out value="${user.getUsername()}"></c:out>!
      </h3>
      <br> Select Your Account
      <br />

      <sf:select class="form-control" id="accountnumbers" path="fromAccountNo">
        <c:forEach var="account" items="${accounts}">
          <option value="${account.getAccountNo()}">${account.getAccountNo()}</option>
        </c:forEach>
      </sf:select>

      <br> Enter the account number
      <br />
      <sf:input type='textbox' class='form-control' path='toOtherAccountNo' />
      <br>

      <br> Enter the amount
      <br />
      <sf:input type="textbox" class="form-control" path="balance" />
      <br>
      <br>
      <br>
      <center>
        <button type="submit" class="btn btn-info">Send Money</button>
      </center>

      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </sf:form>
  </div>
</body>

</html>
