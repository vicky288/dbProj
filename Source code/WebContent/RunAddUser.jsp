<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.functionalities.borrowerMgmt.BorrowerManagement"%>   
<%@ page import="com.functionalities.borrowerMgmt.Borrower"%>    
    
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
	String fName_String = request.getParameter("fnameInput");
	fName_String = fName_String.trim();
	String lName_String = request.getParameter("lnameInput");
	lName_String = lName_String.trim();
	String address_String = request.getParameter("addressInput");
	address_String = address_String.trim();
	String city_String= request.getParameter("cityInput");
	city_String = city_String.trim();
	String state_String = request.getParameter("stateInput");
	state_String = state_String.trim();
	String phone_num_String= request.getParameter("phoneInput");
	phone_num_String = phone_num_String.trim();

	Borrower borrower = new Borrower();
	borrower.setFname(fName_String);
	borrower.setLname(lName_String);
	borrower.setAddress(address_String);
	borrower.setCity(city_String);
	borrower.setState(state_String);
	borrower.setPhone(phone_num_String);
	
	String message = BorrowerManagement.createUserJsp(borrower);	
%> 
<jsp:forward page="AddUser.jsp"> 
	<jsp:param name="addUserMessage" value="<%=message%>"/> 
</jsp:forward> 
</body>
</html>