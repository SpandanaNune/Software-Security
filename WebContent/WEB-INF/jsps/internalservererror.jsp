<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<noscript>
  <h2>JavaScript is disabled! 
  Please enable JavaScript in your web browser!</h2>
  <style type="text/css">
    #main-content { display:none; }
  </style>
</noscript>

<script language="javascript">
  document.onmousedown=disableclick;
  status="Right Click Disabled";
  function disableclick(event)
  {
   if(event.button==2)
    {
      alert(status);
      return false;
    }
  }
</script>

<script>
  window.location.hash="no-back-button";
  window.location.hash="Again-No-back-button";//again because google chrome don't insert first hash into history
  window.onhashchange=function(){window.location.hash="no-back-button";}
</script>
<title>Internal Server Error</title>
<body>
  <br><br>
<div class="container well">
          <center>
          <h1>Internal Server Error<small></small></h1>
          <br/> <br>
          <p>You have encountered a server error. Please navigate back to the Home page.</p>
          <br><br>
          <a href="${pageContext.request.contextPath}/" class="btn btn-large btn-info"><span class="glyphicon glyphicon-home"></span> Home</a>
        </center>
</div>
</body>
</html>
