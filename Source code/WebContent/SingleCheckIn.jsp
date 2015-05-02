<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.functionalities.checkOutCheckIn.BookCheckInExtended"%>
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
	font-size: 14pt;
	color: black;
}
</style>
</head>
<body>
	<h1 class="header" align="center">Library Management System</h1>

	<h1 class="text" align="center">Running CheckIn........</h1>

	<%
		String message = "";
		String date_String = request.getParameter("dateInput");
		if(date_String==null) {
			date_String="default";
		}
		if (date_String =="") {
			date_String="Default";
		}
		String loan_id_String = request.getParameter("loan_id_input");
		loan_id_String = loan_id_String.trim();
		int loan_id = Integer.valueOf(loan_id_String);
		message = BookCheckInExtended.checkIn_Extnded(loan_id, date_String);	
	%>
	<jsp:forward page="CheckIn.jsp">
		<jsp:param name="checkInMessage" value="<%=message%>" />
	</jsp:forward>
</body>
</html>