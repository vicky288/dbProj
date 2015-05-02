<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.ArrayList"%>
    <%@ page import="com.functionalities.fines.Fines"%>
    <%@ page import="com.functionalities.fines.FineResult"%>
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
<script language="JavaScript" type="text/javascript">
function checkform ( form ) {
	var retVal = false;
	var loan_Id_Input_val = form.loanIdInput.value;
	var payment_Input_val = form.paymentValInput.value;
	if (loan_Id_Input_val == "") {
		document.getElementById("p1").innerHTML="Enter value for Loan id.";
		return false;
	}
	if(loan_Id_Input_val !="" && isNaN(loan_Id_Input_val)){
		document.getElementById("p1").innerHTML="Loan Id should be a numeric value.";
		return false;
	} 

	if (payment_Input_val == "") {
		document.getElementById("p1").innerHTML="Enter value for Payment amount to check In.";
		return false;
	}
	if(payment_Input_val !="" && isNaN(payment_Input_val)){
		document.getElementById("p1").innerHTML="Payment value should be a numeric value.";
		return false;
	} 

	
	var oTable = document.getElementById('loan_tableId');
	var rowCount = document.getElementById('loan_tableId').rows.length;
	for (i = 1; i < rowCount; i++) {
		var oCells = oTable.rows.item(i).cells;
		var cellVal = oCells[0].firstChild.data;
		if(cellVal==loan_Id_Input_val){
			retVal = true;
		}
		if(cellVal!=loan_Id_Input_val){
			document.getElementById("p1").innerHTML="Loan Id Not In The List";
		}
	}
	return retVal;
}
</script>
</head>
<body>
<p align="right">
	<input type="button" value="Back" onclick="window.location='FineModule.jsp';" />&nbsp &nbsp
			
	<input type="button" value="Home" onclick="window.location='Home.jsp';" /><br> 
	
</p>
	<h1 class="header" align="center">Library Management System</h1>
	<%
	String message = "";
	String payMsg = request.getParameter("paymentMessage");
	if(payMsg == null) {
		message = "";
	} else {
		message = payMsg; 
	}
	%>

<form action="PayFine.jsp" onsubmit="return checkform(this);">
<table  align="center">

<tr>
	<td colspan="2" align="center"><p id="p1"><h2 style="color:#6A0C07">&nbsp</h2></p></td>
</tr>
<tr>
	<td align="right">Loan Id :&nbsp &nbsp
	</td>
	<td align="left"><input type="text" name="loanIdInput" value="">
	</td>
</tr>
<tr>
	<td align="right">Amount :&nbsp &nbsp
	</td>
	<td align="left"><input type="text" name="paymentValInput" value="">
	</td>
	
</tr>
<tr> 
	<td colspan="2" align="center">
		<input type="submit" name="SubmitCheckIn" value="Pay">
	</td>
</tr>


<tr>
	<td colspan="2" align="center"><h3 style="color:#6A0C07"><%=message %></h3></td>
</tr>

</table>
	
</form>

<table id="loan_tableId" border="1" cellspacing="10" cellpadding="1" width="40%" align="center">
<tr bgcolor="9F3C8A">
	<TH>Loan Id</TH><TH>Fine Amount</TH>
</tr> 
	
	<%
		
		
		//Refresh Fine Table
		Fines.calculateFineAll();
		
		//Retrieve latest fine List
		ArrayList<FineResult> fineList = Fines.getUnPaidFineList();
		for(FineResult fineEntry:fineList ) {
			//System.out.println(fineEntry.getLoan_id()+"&&&&"+fineEntry.getFine_amount());
			
			
			%>
					<tr>
	    						<td align="center"><%=fineEntry.getLoan_id() %></td>
								<td align="center"><%=fineEntry.getFine_amount() %></td>		
					</tr>
			<% 
		}

		
	%>
</table>


</body>
</html>