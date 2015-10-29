<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/navbar.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>Transaction Logs</title>
<noscript>
	<h2>JavaScript is disabled! Why you want to do so? Please enable
		JavaScript in your web browser!</h2>
	<style type="text/css">
#main-content {
	display: none;
}
</style>
</noscript>
<link rel="stylesheet" href="${pageContext.request.contextPath}/welcome">
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
	function radiofn() {

		if (document.getElementById('radioaccount').checked) {
			document.getElementById('radiolabel').innerHTML = 'Account No :';
		} else if (document.getElementById('radiodate').checked) {

			document.getElementById('radiolabel').innerHTML = 'Date :';
		} else if (document.getElementById('radioname').checked) {
			document.getElementById('radiolabel').innerHTML = 'Name :';
		}

	}
</script>

</head>
<body>
	<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="${pageContext.request.contextPath}/welcome"> <b>MTBC
			</b>
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
		<h1>Transaction Log</h1>
		<br>
		<h4>Filter Logs by</h4>

		<sf:form method="POST"
			action="${pageContext.request.contextPath}/transactionlog"
			commandName="transactionLog">
			<sf:errors path="*" cssClass="errorblock" element="div" />
			<table class="table">
				<tr>
					<td align="center"><label for="account">By Account No</label>
						<sf:radiobutton path="logFilter" class="radio" id="radioaccount"
							checked="checked" value="account" name="Filter"
							onchange="radiofn()" /></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td align="center"><label for="date">By Date</label> <sf:radiobutton
							class="radio" id="radiodate" path="logFilter" value="date"
							name="Filter" onchange="radiofn()" /></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td align="center"><label for="name">By Name</label> <sf:radiobutton
							path="logFilter" class="radio" id="radioname" value="name"
							name="Filter" onchange="radiofn()" /></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td><sf:errors class="control" path="logFilter"
							cssClass="error" /></td>
				</tr>
			</table>
			<br>
			<br>
			<center>
				<table>
					<tr>
						<td id="radiolabel" align="center">Account No:</td>
					</tr>
					<tr>
						<td align="center"><div id="radiotext">
								<sf:input class="control form-control" path="input" name="input"
									type="text" />
								<br />
							</div></td>

						<td><sf:errors path="input" Class="error"></sf:errors></td>
					</tr>




				</table>
			</center>
			<br>
			<br>
			<center>
				<input class="control btn btn-info" type="submit" />
			</center>



			<br>
			<br>
			<br>
			<center>
				<TABLE class="table">
					<TR>
						<TH>Transaction Id</TH>
						<TH>Account No</TH>
						<TH>Transaction Type</TH>
						<TH>Amount</TH>
						<TH>Status</TH>
					</TR>
					<div id="outputtable">
						<c:forEach items="${transactions}" var="transaction">
							<TR>
								<TD><c:out
										value="${transaction.getPrimaryKey().getTransactionId()}" /></TD>
								<TD><c:out
										value="${transaction.getPrimaryKey().getAccountNo()}" /></TD>
								<TD><c:out value="${transaction.getAmount()}" /></TD>
								<TD><c:out value="${transaction.getTransactionType()}" /></TD>
								<TD><c:out value="${transaction.getStatus()}" /></TD>

							</TR>
						</c:forEach>
					</div>
				</TABLE>
			</center>
		</sf:form>
	</div>
</body>
</html>
