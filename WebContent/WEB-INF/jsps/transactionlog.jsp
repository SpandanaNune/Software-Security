<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	Transaction Log
	<h2>Filter Logs by</h2>

	<sf:form method="POST" >
		<sf:errors path="*" cssClass="errorblock" element="div" />
		<table>
			<tr>
				<td>Sex :</td>
				<td><sf:radiobutton path="logFilter" value="date" />By date <sf:radiobutton
						path="sex" value="account" />By AccountNo
						<sf:radiobutton
						path="sex" value="F" />By Name</td>
				<td><sf:errors path="sex" cssClass="error" /></td>
			</tr>

		</table>
	</sf:form>
</html>