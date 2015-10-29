<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="navbar.css">
<script src='https://www.google.com/recaptcha/api.js'></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<noscript>
	<h2>JavaScript is disabled! Please enable JavaScript in your web
		browser!</h2>
	<style type="text/css">
#main-content {
	display: none;
}
</style>
</noscript>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/navbar.css">
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
<script>
function validateForm() {
    var alphanum=/^[0-9a-zA-Z]+$/;
    var alphanums=/^[0-9a-zA-Z\s]+$/;
    var alpha= /^[a-zA-Z]+$/;
    var num=/^[0-9]+$/;
    var email=".+@.+\\..+";
    var date="^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
    //var f=0;
    
    var x = document.forms["myForm"]["username"].value;
    
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

   var x1 = document.forms["myForm"]["firstname"].value;
   if (x1.match(alpha) && x1.length>=2 && x1.length<=45) {
	   $("#matchpass1").text("");
		$("#matchpass1").addClass("valid");
		$("#matchpass1").removeClass("error");
    }
   else{
    $("#matchpass1").addClass("error");
	   $("#matchpass1").text("first name must be alphabetic and between 2 and 45 characters");
	   $("#matchpass1").removeClass("valid")
      //f=1;
	   return false;
	  
   }
	   
   


   var x2 = document.forms["myForm"]["lastname"].value;
      if (x2.match(alpha) && x2.length>=2 && x2.length<=45) {
	   $("#matchpass2").text("");
		$("#matchpass2").addClass("valid");
		$("#matchpass2").removeClass("error");
    }
   else{
    $("#matchpass2").addClass("error");
	   $("#matchpass2").text("last name must be alphabetic and between 2 and 45 characters");
	   $("#matchpass2").removeClass("valid")
      //f=1;
	  return false;
	  
   }
 
   var x3 = document.forms["myForm"]["email"].value;
    if (x3.match(email) && x3.length>=4 && x3.length<=45) {
	   $("#matchpass3").text("");
	$("#matchpass3").addClass("valid");
	$("#matchpass3").removeClass("error");
    }
    
   else
   {
   $("#matchpass3").addClass("error");
	$("#matchpass3").text("invalid email id");
	$("#matchpass3").removeClass("valid")
	return false;
	//f=1;
   }

    
    var x4 = document.forms["myForm"]["phone"].value;
       if (x4.match(num) && x4.length>=10 && x4.length<=14) {
	   $("#matchpass4").text("");
		$("#matchpass4").addClass("valid");
		$("#matchpass4").removeClass("error");
    }
   else{
    $("#matchpass4").addClass("error");
	   $("#matchpass4").text("Phone number must be numeric without spaces/dashes");
	   $("#matchpass4").removeClass("valid")
       // f=1;
	   return false;
	  
   }
     
     var x5 = document.forms["myForm"]["SSN"].value;
      if (x5.match(num) && x5.length>=9 && x5.length<=14) {
	   $("#matchpass5").text("");
		$("#matchpass5").addClass("valid");
		$("#matchpass5").removeClass("error");
    }
   else{
    $("#matchpass5").addClass("error");
	   $("#matchpass5").text("SSN must be numeric without spaces/dashes");
	   $("#matchpass5").removeClass("valid")
       // f=1;
	   return false;
	  
   }
      
      var x6 = document.forms["myForm"]["dob"].value;
       if (x6.match(date)) {
	   $("#matchpass6").text("");
	$("#matchpass6").addClass("valid");
	$("#matchpass6").removeClass("error");
    }
    
   else
   {
   $("#matchpass6").addClass("error");
	$("#matchpass6").text("invalid date");
	$("#matchpass6").removeClass("valid")
	return false;
	//f=1;
   }

       
       var x7 = document.forms["myForm"]["Addr1"].value;
          if (x7.match(alphanums) && x7.length>=2 && x7.length<=45) {
	   $("#matchpass7").text("");
		$("#matchpass7").addClass("valid");
		$("#matchpass7").removeClass("error");
    }
   else{
    $("#matchpass7").addClass("error");
	   $("#matchpass7").text("Address1 must be alphanumeric and between 2 and 45 characters");
	   $("#matchpass7").removeClass("valid")
      //f=1;
	   return false;
	  
   }
        
        var x8 = document.forms["myForm"]["Addr2"].value;
          if (x8.match(alphanums) && x8.length>=2 && x8.length<=45) {
	   $("#matchpass8").text("");
		$("#matchpass8").addClass("valid");
		$("#matchpass8").removeClass("error");
    }
   else{
    $("#matchpass8").addClass("error");
	   $("#matchpass8").text("Address2 must be alphanumeric and between 2 and 45 characters");
	   $("#matchpass8").removeClass("valid")
      //f=1;
	   return false;
	  
   }
         
         var x9 = document.forms["myForm"]["City"].value;
               if (x9.match(alpha) && x9.length>=2 && x9.length<=45) {
	   $("#matchpass9").text("");
		$("#matchpass9").addClass("valid");
		$("#matchpass9").removeClass("error");
    }
   else{
    $("#matchpass9").addClass("error");
	   $("#matchpass9").text("City name must be alphabetic, no space and between 2 and 45 characters");
	   $("#matchpass9").removeClass("valid")
      //f=1;
	   return false;
	  
   }
          
          var x10 = document.forms["myForm"]["State"].value;
                 if (x10.match(alpha) && x10.length>=2 && x10.length<=45) {
	   $("#matchpass10").text("");
		$("#matchpass10").addClass("valid");
		$("#matchpass10").removeClass("error");
    }
   else{
    $("#matchpass10").addClass("error");
	   $("#matchpass10").text("State name must be alphabetic, no space and between 2 and 45 characters");
	   $("#matchpass10").removeClass("valid")
      //f=1;
	  return false;
	  
   }
           var x11 = document.forms["myForm"]["Zip"].value;
           if (x11.match(num) && x11.length>=5 && x11.length<=10) {
	   $("#matchpass11").text("");
		$("#matchpass11").addClass("valid");
		$("#matchpass11").removeClass("error");
    }
   else{
    $("#matchpass11").addClass("error");
	   $("#matchpass11").text("Zip code must be numeric without spaces/dashes");
	   $("#matchpass11").removeClass("valid")
       // f=1;
	   return false;
	  
   }
      
        	  
}
</script>
<title>Welcome</title>
</head>

<body>
	​
	<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="${pageContext.request.contextPath}/welcome"> <b>MTBC </b>
			</a>
		</div>
		<div>

			<ul class="nav navbar-nav navbar-right">
				<li><a href="${pageContext.request.contextPath}/welcome"> <span
						class="glyphicon glyphicon-log-in"></span> Login
				</a></li>
			</ul>
		</div>
	</div>
	</nav>
	
	<div class="container">
	<div class="alert alert-info">
   <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
   <strong>Info!</strong> After registering, you will get an email with an OTP. Please enter this OTP in the next page.
 </div>
		<h1>User Registration</h1>
		​
		<sf:form method="post" name="myForm" onsubmit="return validateForm()"
			action="${pageContext.request.contextPath}/registerbtn"
			commandName="user" htmlEscape="true">
      ​
      <table>
				<tr>
					<td class="label">
						<h4 style="color: black">User Name:</h4>
					</td>

					<td><sf:input class="control form-control" path="username"
							name="username" type="text" />
						<div id="matchpass"></div></td>
					<td>&nbsp;</td>
					<td><sf:errors path="username" Class="error"></sf:errors></td>
				</tr>
				<tr>
					<td class="label">
						<h4 style="color: black">First Name:</h4>
					</td>
					<td><sf:input class="control form-control" path="firstname"
							name="firstname" type="text" />
						<div id="matchpass1"></div></td>
					<td>&nbsp;</td>
					<td><sf:errors path="firstname" Class="error"></sf:errors></td>
				</tr>
				<tr>
					<td class="label">
						<h4 style="color: black">Last Name:</h4>
					</td>
					<td><sf:input class="control form-control" path="lastname"
							name="lastname" type="text" />
						<div id="matchpass2"></div></td>
					<td>&nbsp;</td>
					<td><sf:errors path="lastname" Class="error"></sf:errors></td>
				</tr>
				<tr>
					<td class="label">
						<h4 style="color: black">Email:</h4>
					</td>
					<td><sf:input class="control form-control" path="email"
							name="email" type="text" />
						<div id="matchpass3"></div></td>
					<td>&nbsp;</td>
					<td><sf:errors path="email" Class="error"></sf:errors></td>
				</tr>
				​ ​
				<tr>
					<td class="label">
						<h4 style="color: black">Phone:</h4>
					</td>
					<td><sf:input class="control form-control" path="phone"
							name="phone" type="text" />
						<div id="matchpass4"></div></td>
					<td>&nbsp;</td>
					<td><sf:errors path="phone" Class="error"></sf:errors></td>
				</tr>
				<tr>
					<td class="label">
						<h4 style="color: black">SSN:</h4>
					</td>

					<td><sf:input class="control form-control" path="SSN"
							name="SSN" type="password" />
						<div id="matchpass5"></div></td>
					<td>&nbsp;</td>
					<td><sf:errors path="SSN" Class="error"></sf:errors></td>
				</tr>
				<tr>
					<td class="label">
						<h4 style="color: black">
							Date of Birth <br>(mm/dd/yyyy):
						</h4>
					</td>
					<td><sf:input class="control form-control" path="dob"
							name="dob" type="text" />
						<div id="matchpass6"></div></td>
					<td>&nbsp;</td>
					<td><sf:errors path="dob" Class="error"></sf:errors></td>
				</tr>
				<tr>
					<td class="label">
						<h4 style="color: black">Address Line 1:</h4>
					</td>
					<td><sf:input class="control form-control" path="Addr1"
							name="Addr1" type="text" />
						<div id="matchpass7"></div></td>
					<td>&nbsp;</td>
					<td><sf:errors path="Addr1" Class="error"></sf:errors></td>
				</tr>
				<tr>
					<td class="label">
						<h4 style="color: black">Address Line 2:</h4>
					</td>
					<td><sf:input class="control form-control" path="Addr2"
							name="Addr2" type="text" />
						<div id="matchpass8"></div></td>
					<td>&nbsp;</td>
					<td><sf:errors path="Addr2" Class="error"></sf:errors></td>
				</tr>
				<tr>
					<td class="label">
						<h4 style="color: black">City:</h4>
					</td>
					<td><sf:input class="control form-control" path="City"
							name="City" type="text" />
						<div id="matchpass9"></div></td>
					<td>&nbsp;</td>
					<td><sf:errors path="City" Class="error"></sf:errors></td>
				</tr>
				<tr>
					<td class="label">
						<h4 style="color: black">State:</h4>
					</td>
					<td><sf:input class="control form-control" path="State"
							name="State" type="text" />
						<div id="matchpass10"></div></td>
					<td>&nbsp;</td>
					<td><sf:errors path="State" Class="error"></sf:errors></td>
				</tr>
				<tr>
					<td class="label">
						<h4 style="color: black">ZIP Code:</h4>
					</td>
					<td><sf:input class="control form-control" path="Zip"
							name="Zip" type="text" />
						<div id="matchpass11"></div></td>
					<td>&nbsp;</td>
					<td><sf:errors path="Zip" Class="error"></sf:errors></td>
				</tr>
				<tr>
					<td class="label">
						<h4 style="color: black">Verify Captcha:</h4>
					</td>
					<td>
						<div class="g-recaptcha"
							data-sitekey="6LcHCw8TAAAAAIqGUaZBHaZbJ4ra61tME5Lz3zB7"></div>
					</td>
				</tr>
			</table>
			<br>
			<center>
				<input class="control btn btn-info" value="Register" type="submit" />
			</center>
			<br> ​
      <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</sf:form>
	</div>
</body>

</html>
