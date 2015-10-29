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
	   $("#matchpass1").text("Company name must be alphabetic and between 2 and 45 characters");
	   $("#matchpass1").removeClass("valid")
      //f=1;
	   return false;
	  
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
 	   $("#matchpass5").text("TIN must be numeric without spaces/dashes");
 	   $("#matchpass5").removeClass("valid")
        // f=1;
 	   return false;
 	  
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
	   $("#matchpass9").text("City name must be alphabetic without space and between 2 and 45 characters");
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
	   $("#matchpass10").text("State name must be alphabetic without space and between 2 and 45 characters");
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
<title>Edit Merchant Profile</title>
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
	<div class="alert alert-info">
  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
  <strong>Info!</strong> You are required to upload a private key to update your TIN.
</div>
		<h1>Edit Merchant Profile</h1>
		​
		<sf:form method="post" name="myForm" onsubmit="return validateForm()"
			action="${pageContext.request.contextPath}/editmerchantprofiledone"
			commandName="user" enctype="multipart/form-data" htmlEscape="true" >
      ​
     
      <table>
				<tr>
					<td class="label">
						<h4 style="color: black">UserName:</h4>
					</td>
					<td><sf:input class="control form-control" path="username"
							name="username" type="text" readonly="true" /> <br /> <sf:errors
							path="username" Class="error"></sf:errors>
						<div id="matchpass"></div></td>
				</tr>
				<tr>
					<td class="label">
						<h4 style="color: black">CompanyName:</h4>
					</td>
					<td><sf:input class="control form-control" path="firstname"
							name="firstname" type="text" /> <br /> <sf:errors
							path="firstname" Class="error"></sf:errors>
						<div id="matchpass1"></div></td>

				</tr>


				<tr>
					<td class="label">
						<h4 style="color: black">Phone:</h4>
					</td>
					<td><sf:input class="control form-control" path="phone"
							name="phone" type="text" /> <br /> <sf:errors path="phone"
							Class="error"></sf:errors>
						<div id="matchpass4"></div></td>
				</tr>

				<tr>
					<td class="label">
						<h4 style="color: black">Address Line 1:</h4>
					</td>
					<td><sf:input class="control form-control" path="Addr1"
							name="Addr1" type="text" /> <br /> <sf:errors path="Addr1"
							Class="error"></sf:errors>
						<div id="matchpass7"></div></td>
				</tr>
				<tr>
					<td class="label">
						<h4 style="color: black">Address Line 2:</h4>
					</td>
					<td><sf:input class="control form-control" path="Addr2"
							name="Addr2" type="text" /> <br /> <sf:errors path="Addr2"
							Class="error"></sf:errors><div id="matchpass8"></div></td>
				</tr>
				<tr>
					<td class="label">
						<h4 style="color: black">City:</h4>
					</td>
					<td><sf:input class="control form-control" path="City"
							name="City" type="text" /> <br /> <sf:errors path="City"
							Class="error"></sf:errors>
						<div id="matchpass9"></div></td>
				</tr>
				<tr>
					<td class="label">
						<h4 style="color: black">State:</h4>
					</td>
					<td><sf:input class="control form-control" path="State"
							name="State" type="text" /> <br /> <sf:errors path="State"
							Class="error"></sf:errors>
						<div id="matchpass10"></div></td>
				</tr>
				​
				<tr>
					<td class="label">
						<h4 style="color: black">ZIP Code:</h4>
					</td>
					<td><sf:input class="control form-control" path="Zip"
							name="Zip" type="text" /> <br /> <sf:errors path="Zip"
							Class="error"></sf:errors>
						<div id="matchpass11"></div></td>
				</tr>

				<tr>
					<td class="label">
						<h4 style="color: black">TIN:</h4>
					</td>
					<td><sf:input class="control form-control" path="SSN"
							name="SSN" type="password" />
						<div id="matchpass5"></div></td>
				</tr>
			</table>
			<br />
			<br> ​
      <br>
			<center>
				<input type="file" name="file" /> <input type="hidden"
					name="${_csrf.parameterName}" value="${_csrf.token}" /> <br /><br /> <input
					class="control btn btn-info" value="Update" type="submit" /> <br />
				<br>

				<sf:errors path="SSN" Class="error"></sf:errors>

			</center>
      ​
      <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</sf:form>
	</div>
</body>

</html>
