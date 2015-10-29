<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <script>
function validateForm() {
   
	var alphanum=/^[0-9a-zA-Z]+$/;
    var pass=/^[0-9a-zA-Z_@]+$/;
	var x = document.forms["f"]["username"].value;
    
	   if (x.match(alphanum) && x.length>=8 && x.length<=45) {
		   $("#matchpass").text("");
		$("#matchpass").addClass("valid");
		$("#matchpass").removeClass("error");
	    }
	    
	   else
	   {
	   $("#matchpass").addClass("error");
		$("#matchpass").text("username must be alphanumeric and between 8 and 45 characters");
		$("#matchpass").removeClass("valid")
		return false;
		//f=1;
	   }
	   
	   var x1 = document.forms["f"]["password"].value;
	   if (x1.match(pass) && x1.length>=5 && x1.length<=100) {
		   $("#matchpass1").text("");
		$("#matchpass1").addClass("valid");
		$("#matchpass1").removeClass("error");
	    }
	    
	   else
	   {
	   $("#matchpass1").addClass("error");
		$("#matchpass1").text("password can contain only alphabets, letters and special characters _ @ ");
		$("#matchpass1").removeClass("valid")
		return false;
		//f=1;
	   }

}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	My Login Page
	<br />

	<sf:form method="post" name='f' onsubmit="return validateForm()" 
		action="${pageContext.request.contextPath}/loginbtn"
		commandName="userlogin" htmlEscape="true" >

		<table class="formtable">
			<tr>
				<td class="label">UserName:</td>
				<td><sf:input class="control" path="username" name="name"
						type="text" /><br /> <sf:errors path="username" Class="error"></sf:errors><div id="matchpass"></div></td></td>
			</tr>
			<tr>
				<td class="label">Password:</td>
				<td><sf:input class="control" path="password" name="password"
						type="text" /><br /> <sf:errors path="password" Class="error"></sf:errors><div id="matchpass1"></div></td></td>
			</tr>
			
			<tr>
				<td class="label"></td>
				<td><input class="control" value="Login" type="submit" /></td>
			</tr>
		</table>

	</sf:form>

</body>
</html>
