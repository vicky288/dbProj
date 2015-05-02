<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.functionalities.checkOutCheckIn.BookCheckOut"%>
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
</head>
<body>
<h1 class="header" align="center">Library Management System</h1>

<h1 class="text" align="center">Running CheckOut........</h1>

<%
	String book_id = request.getParameter("bookIdInput");
	book_id = book_id.trim();
	String branch_id = request.getParameter("branchIdInput");
	branch_id = branch_id.trim();
	String card_no = request.getParameter("cardNoInput");
	card_no = card_no.trim();

	String message = BookCheckOut.checkOutJspString(book_id, branch_id, card_no);	
%> 
<jsp:forward page="CheckOut.jsp"> 
	<jsp:param name="checkOutMessage" value="<%=message%>"/> 
</jsp:forward> 

</body>
</html>