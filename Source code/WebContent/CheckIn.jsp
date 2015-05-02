<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Library Management System</title>
<style>
body {
	background-image: url(./pic.jpg);
	background-size: 100%;
	background-repeat: no-repeat;
	background-attachment: fixed;
}
h1.header{
	font-size:40pt;
	color:#780000;
	align:center;
}
h1.text{
	font-size:14pt;
	color:black ;
}
</style>
<script language="JavaScript" type="text/javascript">
function checkform ( form ) {
	var val = form.cardNoInput.value;
	var retVal = true;
	
	if (form.bookIdInput.value == "" && form.borrowerNameInput.value == "" && form.cardNoInput.value == "") {
		document.getElementById("p1").innerHTML="All Fields Can't be empty.";
		retVal = false;
	}
	
	if(val!="" && isNaN(val)){
		document.getElementById("p1").innerHTML="Card no should be a numeric value.";
		retVal = false;
	}
	
	return retVal;
}
</script>
</head>
<body>

<p align="right">
	<input type="button" value="Back" onclick="window.location='CheckInOut.jsp';" />&nbsp &nbsp
			
	<input type="button" value="Home" onclick="window.location='Home.jsp';" /><br> 
	
</p>
<h1 class="header" align="center">Library Management System</h1>

<%
	String message="This is Test";
	String checkInMsg = request.getParameter("checkInMessage");
	if(checkInMsg == null) {
		message = "";
	} else {
		message = checkInMsg; 
	}
	
%>


<h1 class="text" align="center">Enter the details to Locate the checked out Book(s).</h1>
<br>
<form action="RunCheckIn.jsp" onsubmit="return checkform(this);">


<table width="50%" align="center">
<tr>
	<td align="right">Book Id :&nbsp &nbsp
	</td>
	<td align="left"><input type="text" name="bookIdInput" value="">
	</td>
</tr>
<tr>
	<td align="right">Card Number :&nbsp &nbsp
	</td>
	<td align="left"><input type="text" name="cardNoInput" value="">
	</td>
	
</tr>
<tr>
	<td align="right">Borrower Name :&nbsp &nbsp 
	</td>
	<td><input type="text" name="borrowerNameInput" value="">
	</td>
	
</tr>
<tr> 
	<td colspan="2" align="center">
		<input type="submit" name="SubmitCheckIn" value="Get List">
	</td>
</tr>	
<tr>
	<td colspan="2" align="center">&nbsp</td>
</tr>

<tr>
	<td colspan="2" align="center"><p id="p1"><h2 style="color:#6A0C07">&nbsp</h2></p></td>
</tr>
<tr>
	<td colspan="2" align="center"><h2 style="color:#6A0C07"><%=message %></h2></td>
</tr>
</table>
<br>

</form>

</body>
</html>