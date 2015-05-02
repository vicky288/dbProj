<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.functionalities.fines.Fines"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	String message= "Payfine.Jsp";
	String loan_id_String = request.getParameter("loanIdInput");
	loan_id_String = loan_id_String.trim();
	int loan_id = 0;
	if(loan_id_String.equals("")) {
		loan_id = 0;
	} else {
		loan_id = Integer.valueOf(loan_id_String);
	}

	String payment_amount_String = request.getParameter("paymentValInput");
	payment_amount_String = payment_amount_String.trim();
	float payment_amount = 0.0f;
	if(payment_amount_String.equals("")) {
		payment_amount = 0.0f;
	} else {
		payment_amount = Float.valueOf(payment_amount_String);
	}
	message=Fines.payFine(loan_id, payment_amount);


%>
	<jsp:forward page="Fine.jsp">
	<jsp:param name="paymentMessage" value="<%=message%>" />
	</jsp:forward>
</body>
</html>