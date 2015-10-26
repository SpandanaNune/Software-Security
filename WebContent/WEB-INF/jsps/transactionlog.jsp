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
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>Insert title here</title>


</head>
<body>
	<div class="container">
		<h1>Transaction Log</h1>
		<br> <br> <br>
		<h3>Filter Logs by</h3>

		<sf:form method="POST"
			action="${pageContext.request.contextPath}/transactionlog"
			commandName="transactionLog">
			<sf:errors path="*" cssClass="errorblock" element="div" />
			<table>
				<tr>
					<td><sf:radiobutton path="logFilter" class="control radio"
							id="radioaccount" checked="checked" value="account" name="Filter"
							onchange="radiofn()" /><font style="color: black">By
							AccountNo</font></td>
					<td><sf:radiobutton class="control radio" id="radiodate"
							path="logFilter" value="date" name="Filter" onchange="radiofn()" /><font
						style="color: black">By Date</font></td>
					<td><sf:radiobutton path="logFilter" class="control radio"
							id="radioname" value="name" name="Filter" onchange="radiofn()" /><font
						style="color: black">By Name</font></td>
					<td><sf:errors class="control" path="logFilter"
							cssClass="error" /></td>
				</tr>

				<tr>
					<td class="label" id="radiolabel">
							Account No: 
						</td>
					<td><div id="radiotext">
							<sf:input class="control form-control" path="input" name="input"
								type="text" />
							<br />
						</div></td>

					<td><sf:errors path="input" Class="error"></sf:errors></td>
				</tr>




			</table>
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
			<br>
			<br>
			<center>
				<input class="control btn btn-info" type="submit" />
			</center>



			<br>
			<br>
			<br>
			<center>
				<TABLE BORDER="1">
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
