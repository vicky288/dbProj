<%@page import="com.functionalities.fines.FineResultUser"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.ArrayList"%>
        <%@ page import="com.functionalities.fines.Fines"%>
         <%@ page import="com.functionalities.fines.FineResultUser"%>
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
</style>
</head>
<body>
<p align="right">
	<input type="button" value="Back" onclick="window.location='Home.jsp';" />&nbsp &nbsp
			
	<input type="button" value="Home" onclick="window.location='Home.jsp';" /><br> 
	
</p>
 <h1 class="header" align="center">Library Management System</h1>

<table id="loan_tableId" border="1" cellspacing="10" cellpadding="1" width="40%" align="center">
<tr bgcolor="9F3C8A">
	<TH>Card No</TH><TH>Total Amount</TH>
</tr> 
	
	<%
		
		
		//Refresh Fine Table
		Fines.calculateFineAll();
		
		//Retrieve latest fine List
		ArrayList<FineResultUser> fineList = Fines.getUnPaidFineListGrouped();
		for(FineResultUser fineEntry:fineList ) {
			//System.out.println(fineEntry.getCard_no()+"&&&&"+fineEntry.getTotal_fine_amount());
			
			
			%>
					<tr>
	    						<td align="center"><%=fineEntry.getCard_no() %></td>
								<td align="center"><%=fineEntry.getTotal_fine_amount() %></td>		
					</tr>
			<% 
		}

		
	%>
</table>
<p align="middle">
	 <input type="button" value="Pay Fines"
				onClick="window.location='Fine.jsp';" />
</p>
</body>
</html>