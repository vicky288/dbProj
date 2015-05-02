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
	if (form.fnameInput.value != "" && form.lnameInput.value != "" && form.addressInput.value !="" && form.cityInput.value !="" && form.stateInput.value !="") {
		return true;
	} else {
		document.getElementById("p1").innerHTML="Name and Address fields are mandatory!";
		document.getElementById("p2").innerHTML="";
		return false;
	}
}
</script>
</head>
<body>
<p align="right">
	<input type="button" value="Back" onclick="window.location='Home.jsp';" />&nbsp &nbsp
			
	<input type="button" value="Home" onclick="window.location='Home.jsp';" /><br> 
	
</p>
<h1 class="header" align="center">Library Management System</h1>

<%
	String message="This is Test";
	String addUsrMsg = request.getParameter("addUserMessage");
	if(addUsrMsg == null) {
		message = "";
	} else {
		message = addUsrMsg; 
	}
	
%>


<h1 class="text" align="center">Enter the details to add a borrower.</h1>
<br>
<form action="RunAddUser.jsp" onsubmit="return checkform(this);">


<table width="50%" align="center">
<tr>
	<td align="right">First Name :&nbsp &nbsp
	</td>
	<td align="left"><input type="text" name="fnameInput" value="">
	</td>
</tr>
<tr>
	<td align="right">Last Name :&nbsp &nbsp
	</td>
	<td align="left"><input type="text" name="lnameInput" value="">
	</td>
</tr>
<tr>
	<td align="right">Address :&nbsp &nbsp 
	</td>
	<td><input type="text" name="addressInput" value="">
	</td>
	
</tr>
<tr>
	<td align="right">City :&nbsp &nbsp
	</td>
	<td align="left"><input type="text" name="cityInput" value="">
	</td>
	
</tr>
<tr>
	<td align="right">State :&nbsp &nbsp
	</td>
	<td align="left"><input type="text" name="stateInput" value="">
	</td>
	
</tr>
<tr>
	<td align="right">Phone Number :&nbsp &nbsp
	</td>
	<td align="left"><input type="text" name="phoneInput" value="">
	</td>
	
</tr>
<tr> 
	<td colspan="2" align="center">
		<input type="submit" name="SubmitAddUser" value="Add User">
	</td>
</tr>	

<tr>
	<td colspan="2" align="center"><p id="p1"><h2 style="color:#6A0C07">&nbsp</h2></p></td>
</tr>
<tr>
	<td colspan="2" align="center"><h2 style="color:#6A0C07" id="p2"><%=message %></h2></td>
</tr>
</table>
<br>

</form>

</body>
</html>