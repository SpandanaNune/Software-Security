<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/static/css/main.css"
	rel="stylesheet" type="text/css" />

<title>Insert title here</title>
</head>
<body>
View/Edit User details.
	<table class="offers">
		<tr>
			<td>UserName</td>
			<td>FirstName</td>
			<td>LastName</td>
			<td>Email</td>
			<td>Action</td>
		</tr>

		<c:forEach var="user" items="${user}">
			<tr>

				<td><c:out value="${user.getUsername()}"></c:out></td>

				<td><c:out value="${user.getFirstname()}"></c:out></td>

				<td><c:out value="${user.getLastname()}"></c:out></td>

				<td><c:out value="${user.getEmail()}"></c:out></td>
				<td><form method="post"
						action="${pageContext.request.contextPath}/editbtn">
						<input type="hidden" name="View/Edit" value="${user.getUsername()}" />
						<input class="control" value="View/Edit" type="submit" /> <input
							type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form></td>
				<%-- <td><form method="post"
						action="${pageContext.request.contextPath}/deletebtn">
						<input type="hidden" name="Edit" value="${user.getUsername()}" />
						<input class="control" value="Delete" type="submit" /> <input
							type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form></td> --%>

			</tr>
		</c:forEach>
	</table>

</body>
</html>