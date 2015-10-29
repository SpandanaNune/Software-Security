<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
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
  window.location.hash="Again-No-back-button";
  window.onhashchange=function(){window.location.hash="no-back-button";}
</script>
<title>Error!</title>
<body>
  <br><br>
<div class="container well">
          <center>
          <h1>Method not allowed<small><font face="Tahoma" color="red">&nbsp;Error 405</font></small></h1>
          <br/> <br>
          <p>Method is not supported.</p>
          <br><br>
          <a href="${pageContext.request.contextPath}/" class="btn btn-large btn-info"><span class="glyphicon glyphicon-home"></span> Home</a>
        </center>
</div>
</body>
</html>
