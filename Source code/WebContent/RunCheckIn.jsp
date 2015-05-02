<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.TreeMap"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.functionalities.checkOutCheckIn.BookCheckIn"%>
<%@ page import="com.functionalities.checkOutCheckIn.BookLoanResults"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
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
<script language="JavaScript" type="text/javascript">
$( document ).ready(function() {
	$( "#datepicker" ).datepicker();
	});
function checkform ( form ) {
	var retVal = false;
	var loan_Id_Input_val = form.loan_id_input.value;
	var date_Input_val = form.dateInput.value;
	if (loan_Id_Input_val == "") {
		document.getElementById("p1").innerHTML="Enter value for Loan id to check In.";
		return false;
	}
	if(loan_Id_Input_val !="" && isNaN(loan_Id_Input_val)){
		document.getElementById("p1").innerHTML="Loan Id should be a numeric value.";
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
	<input type="button" value="Back" onclick="window.location='CheckIn.jsp';" />&nbsp &nbsp
			
	<input type="button" value="Home" onclick="window.location='Home.jsp';" /><br> 
	
</p>
	<h1 class="header" align="center">Library Management System</h1>
	<%
	String message = "";
	String book_id = request.getParameter("bookIdInput");
	book_id = book_id.trim();
	String name = request.getParameter("borrowerNameInput");
	name = name.trim();
	String card_no_String = request.getParameter("cardNoInput");
	card_no_String = card_no_String.trim();
	int card_no = 0;
	if(card_no_String.equals("")) {
		card_no = 0;
	} else {
		card_no = Integer.valueOf(card_no_String);
	}
	
	
	Map<String,ArrayList> map = BookCheckIn.getCheckedOutListJsp(book_id, card_no, name);
	
	Set<String> keySet = map.keySet();
	Iterator<String> itKey = keySet.iterator();
    while(itKey.hasNext()){
    	//System.out.println(itKey.next()); //either this or next line
    	message = (String) itKey.next();
    }
    if (!message.equals("_"))
    {   
    %>

<jsp:forward page="CheckIn.jsp"> 
	<jsp:param name="checkInMessage" value="<%=message%>"/> 
</jsp:forward>     
    <%
    }
    %>
  <form action="SingleCheckIn.jsp" onsubmit="return checkform(this);">
 <table id="loan_tableId" border="1" cellspacing="10" cellpadding="1" width="80%" align="center">
<tr bgcolor="9F3C8A">
	<TH>Loan Id</TH><TH>Book Id</TH><TH>Branch Id</TH><TH>Card No</TH><TH>Check Out Date</TH><TH>Due Date</TH>
</tr>   
    
    <%
    Collection<ArrayList> values = map.values();
    Iterator<ArrayList> itVal = values.iterator();
    ArrayList<BookLoanResults> loanList = null;
    while(itVal.hasNext()){
    	//System.out.println(itVal.next());
    	loanList = (ArrayList)itVal.next();
    }

	for (BookLoanResults result:loanList) {
		System.out.println(result.getLoan_id()+"@@@@@@"+ result.getBook_id() +"@@@@@@"+result.getBranch_id()+"@@@@@@"+result.getCard_no()+"######"+result.getDate_out()+"######"+result.getDue_date());
		
		%>
		<tr>
	    						<td align="center"><%=result.getLoan_id() %></td>
								<td align="center"><%=result.getBook_id() %></td>
								<td align="center"><%=result.getBranch_id() %></td>
								<td align="center"><%=result.getCard_no() %></td>
								<td align="center"><%=result.getDate_out() %></td>
								<td align="center"><%=result.getDue_date() %></td>
		</tr>
	    
	    <%	
		
		}
		%>
</table>
<p align="center">
Enter the Loan Id to Check In :
<input type="text" name="loan_id_input" value="">&nbsp &nbsp &nbsp
Check In Date : <input type="text" id="datepicker" name="dateInput">&nbsp &nbsp &nbsp
<input type="submit" name="Chech In" value="Check In">

</p>
<br> 
</form>	

<table  align="center">
<tr>
	<td colspan="2" align="center"><p id="p0"><h3>*Select Date, if Check-In date is not Today's date.</h3></p></td>
</tr>
<tr>
	<td colspan="2" align="center"><p id="p1"><h2 style="color:#6A0C07">&nbsp</h2></p></td>
</tr>
</table>
<p align="center">
<h2 style="color:#6A0C07"><%=message %></h2>
</p>	
	
	
	
	
	
	
	
	
	
	
	
	<%
	
	%>
    
    
    <%
    
    %>
</body>
</html>