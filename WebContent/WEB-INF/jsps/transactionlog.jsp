<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>Insert title here</title>
<script type="text/javascript">


          function datefn() {
        	  document.getElementById('byaccount').style.visibility = 'hidden';
              document.getElementById('byname').style.visibility = 'hidden';
              document.getElementById('byaccountlabel').style.visibility = 'hidden';
              document.getElementById('bynamelabel').style.visibility = 'hidden';
              document.getElementById('bydate').style.visibility = 'visible';
             

              document.getElementById('bydatelabel').style.visibility = 'visible';
             
          }
          function accountfn() {
        	  document.getElementById('bydate').style.visibility = 'hidden';
              document.getElementById('byname').style.visibility = 'hidden';
              document.getElementById('bydatelabel').style.visibility = 'hidden';
              document.getElementById('bynamelabel').style.visibility = 'hidden';
              document.getElementById('byaccount').style.visibility = 'visible';
             

              document.getElementById('byaccountlabel').style.visibility = 'visible';
             
          }
          function namefn() {
        	  document.getElementById('byaccount').style.visibility = 'hidden';
        	  document.getElementById('byaccountlabel').style.visibility = 'hidden';
              document.getElementById('bydatelabel').style.visibility = 'hidden';
          
              document.getElementById('bydate').style.visibility = 'hidden';
              document.getElementById('byname').style.visibility = 'visible';
             

              document.getElementById('bynamelabel').style.visibility = 'visible';
          }
</script>

</head>
<body>
  <div class="container">
	<h1>Transaction Log</h1>
  <br><br><br>
	<h3>Filter Logs by</h3>

	<sf:form method="POST" action="${pageContext.request.contextPath}/transactionlog" commandName="transactionLog" >
		<sf:errors path="*" cssClass="errorblock" element="div" />
    <table>
                <tr>
                <td>  <sf:radiobutton
                                    path="logFilter" class="control radio" checked="checked" value="account" name="Filter" onchange="accountfn()"/><font style="color:black">By AccountNo</font>
                           </td>
                    <td>  <sf:radiobutton class="control radio" path="logFilter" value="date" name="Filter" onchange="datefn()"/><font style="color:black">By Date</font>
                        </td><td>   <sf:radiobutton
                                    path="logFilter" class="control radio" value="name" name ="Filter" onchange="namefn()"/><font style="color:black">By Name</font></td>
                    <td><sf:errors class="control" path="logFilter" cssClass="error" /></td>
                </tr>

                       <tr>
                           <td class="label"><div id="bydatelabel"><h4 style="color:black">Date: (MM/DD/YYYY) &nbsp;</h4></div></td>
                           <td><div id="bydate"><sf:input class="control form-control" path="date" name="date"
                                   type="text" /><br /> </div> </td>

                                <td>   <sf:errors path="date" Class="error"></sf:errors></td>
                       </tr>

                         <tr>
                             <td class="label"><div id="byaccountlabel"><h4 style="color:black">Account No:</h4></div></td>
                             <td><div id="byaccount"><sf:input class="control form-control" path="accountNo" name="accountNo"
                                     type="text" /><br /></div> </td>

                                  <td>   <sf:errors path="accountNo" Class="error"></sf:errors></td>
                         </tr>


                         <tr>
                             <td class="label"><div id="bynamelabel"><h4 style="color:black">User Name:</h4></div></td>
                             <td><div id="byname"><sf:input class="control form-control" path="name" name="name"
                                     type="text" /><br /> </div> </td>

                                  <td>   <sf:errors path="accountNo" Class="error"></sf:errors> </td>
                         </tr>


                </table>
      <br><br>
			<center><input class="control btn btn-info" type="submit" /></center>



      <br><br><br>
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
