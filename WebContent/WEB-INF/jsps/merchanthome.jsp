<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language="javascript">
document.onmousedown=disableclick;
status="Right Click Is Disabled For The Website";
function disableclick(event)
{
  if(event.button==2)
   {
     alert(status);
     return false;
   }
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/static/css/main.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>Merchant Home</title>
</head>
<body>
  <div class="container">
	<H1>Merchant Home</H1>
  <h3> Hi <c:out value="${user.getUsername()}"></c:out> ! </h3>
  <div id="border" class="well">
    <center>
      <h4> What would you like to do??</h4> <br>
			<a class="btn btn-info btn-large"
				href="${pageContext.request.contextPath}/transactionhistory">View
				Statement</a><br /> <br /> <br><a class="btn btn-info btn-large"
				href="${pageContext.request.contextPath}/editmerchant">View/Edit User
				Details</a><br /> <br />  <br />
				<a class="btn btn-info btn-large"
				href="${pageContext.request.contextPath}/openMerchantTransaction">Make Transaction</a><br /> <br />
		<br><br>
  </center>
  </div>
	</div>
</body>
</html>