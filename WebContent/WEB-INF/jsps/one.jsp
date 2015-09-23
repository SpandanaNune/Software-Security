
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>
one
</h1>
<c:out value="${name}"></c:out>

	<!-- <h2>Results</h2> -->

	<c:forEach var="user" items="${offers}">
	    Userid ${user.userid}<br/>
	    Username ${user.username}<br/>
	</c:forEach> 