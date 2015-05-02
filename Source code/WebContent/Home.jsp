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

h1.header {
	font-size: 40pt;
	color: #780000;
	align: center;
}

h1.text {
	font-size: 20pt;
	color: red;
}

.butt_search {
	background: url(./a1.jpg) no-repeat;
	border: 1px outset blue;
	height: 220px;
	width: 220px;
	cursor: pointer;
}

.butt_check {
	background: url(./a2.jpg) no-repeat;
	border: 1px outset blue;
	height: 220px;
	width: 220px;
	cursor: pointer;
}

.butt_addUser {
	background: url(./a3.jpg) no-repeat;
	border: 1px outset blue;
	height: 220px;
	width: 220px;
	cursor: pointer;
}
</style>
</head>
<body>
	<h1 class="header" align="center">Library Management System</h1>

	<form action="Search.jsp">
		<p align="center">

			<input class="butt_search" type="button"
				onclick="window.location='Search.jsp';" />&nbsp &nbsp &nbsp &nbsp
			&nbsp &nbsp <input class="butt_check" type="button"
				onclick="window.location='CheckInOut.jsp';" /><br>
			<br>
			<br> <input class="butt_addUser" type="button"
				onClick="window.location='AddUser.jsp';" />
			<br>
			<br> <input type="button" value="Show Current Fines"
				onClick="window.location='FineModule.jsp';" />
		</p>
	</form>



</body>
hi
</html>