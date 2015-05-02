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
	font-size:20pt;
	color:black;
}
.butt_checkIn {
	background: url(./checkin.jpg) no-repeat;
	border: 1px outset blue;
	height: 170px;
	width: 170px;
	cursor: pointer;
}

.butt_checkOut {
	background: url(./checkout.jpg) no-repeat;
	border: 1px outset blue;
	height: 170px;
	width: 170px;
	cursor: pointer;
}
</style>
</head>
<body>
<p align="right">
	<input type="button" value="Back" onclick="window.location='Home.jsp';" />&nbsp &nbsp
			
	<input type="button" value="Home" onclick="window.location='Home.jsp';" /><br> 
	
</p>
<h1 class="header" align="center">Library Management System</h1>
<h1 class="text" align="center">Choose an Operation.</h1>

		<p align="center">

			<input class="butt_checkIn" type="button"
				onclick="window.location='CheckIn.jsp';" />&nbsp &nbsp &nbsp &nbsp
			&nbsp &nbsp <input class="butt_checkOut" type="button"
				onclick="window.location='CheckOut.jsp';" /><br>
			<br>
			<br> 
		</p>
</body>
</html>