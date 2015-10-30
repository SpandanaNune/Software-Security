<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	var count=0;
    function disableclick(event) {
      if (event.button == 2) {
        alert(status);
        return false;
      }
    }
    function disableMe()
    {
    	alert("Email is being sent!! Please wait to be redirected to the next page!!");
    	if(count ==0)
    		{
    		count =1;
    		return true;
    		}
    	else 
    		return false;
    }
  </script>

  <script>
    window.location.hash = "no-back-button";
    window.location.hash = "Again-No-back-button";
    window.onhashchange = function() {
      window.location.hash = "no-back-button";
    }
  </script>
  <title>Transaction History</title>
  
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
  <div class="alert alert-info">
   <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
   <strong>Info!</strong> You will receive an email after you proceed. 
 </div>
    <sf:form method="post" htmlEscape="true" >
      <H1>Transaction History</H1>
      <TABLE class="table table-hover">
        <TR>
          <TH>Transaction ID</TH>
          <TH>Account No</TH>
          <TH>Transaction Type</TH>
          <TH>Amount</TH>
          <TH>Status</TH>
        </TR>
        <c:forEach items="${transactions}" var="transaction">
          <TR>
            <TD>
              <c:out value="${transaction.getPrimaryKey().getTransactionId()}" />
            </TD>
            <TD>
              <c:out value="${transaction.getPrimaryKey().getAccountNo()}" />
            </TD>
            <TD>
              <c:out value="${transaction.getAmount()}" />
            </TD>
            <TD>
              <c:out value="${transaction.getTransactionType()}" />
            </TD>
            <TD>
              <c:out value="${transaction.getStatus()}" />
            </TD>

          </TR>
        </c:forEach>

      </TABLE>
      <!--  <button class="btn btn-primary btn-large" type="submit" onClick="showAlert()">Download</button>
	 	 <button class="btn btn-primary btn-large" type="submit" onClick="showAlert()">E-Mail</button> -->

    </sf:form>
    <a class="btn btn-info btn-primary btn-large" href="${pageContext.request.contextPath}/emailTransactions" onclick="return disableMe();" type="submit"> Email </a>
  </div>
</body>

</html>
