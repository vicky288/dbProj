<%@page import="com.functionalities.search.BookSearchNew"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.functionalities.search.SearchResult_New"%>
<%@ page import="com.functionalities.search.BookSearch"%>
<%@ page import="com.functionalities.search.BookSearchNew"%>
<%@ page import="com.functionalities.search.BranchAvailability"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.HashMap"%>

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
<p align="right">
	<input type="button" value="Back" onclick="window.location='Search.jsp';" />&nbsp &nbsp
			
	<input type="button" value="Home" onclick="window.location='Home.jsp';" /><br> 
	
</p>
	<h1 class="header" align="center">Library Management System</h1>



	<%
		String book_id = request.getParameter("bookIdInput");
		book_id = book_id.trim();
		String title = request.getParameter("titleInput");
		title = title.trim();
		String authorName = request.getParameter("authorNameInput");
		if(authorName!=null) {
			authorName = authorName.trim();
		} else {
			authorName = "";
		}
		String fName = request.getParameter("firstNameInput");
		if (fName!=null) {
			fName = fName.trim();
		} else {
			fName ="";
		}
		String mName = request.getParameter("middleNameInput");
		if (mName!=null) {
			mName = mName.trim();
		} else {
			mName ="";
		}
		String lName = request.getParameter("lastNameInput");
		if (lName!=null) {
			lName = lName.trim();
		} else {
			lName ="";
		}
		
		System.out.println(fName+"^^^^"+mName+"$$$$$"+lName+"*****"+authorName);
		try {
			//ArrayList<SearchResult_New> searchResultList = search("", "system", "bernd"); 
			ArrayList<SearchResult_New> searchResultList = new ArrayList<SearchResult_New>();
			if (fName.equals("") && mName.equals("") && lName.equals("") && authorName.equals("")) {
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^###############");
				searchResultList = BookSearch.search(book_id, title, authorName);
			} 
			else if(authorName.equals("")) {
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				searchResultList = BookSearchNew.searchExtended(book_id, title, fName, mName, lName);
			} 
			else if (fName.equals("") && mName.equals("") && lName.equals("")) {
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^***************");
				searchResultList = BookSearch.search(book_id, title, authorName);
			} 

			
				//	BookSearch.search(book_id, title, authorName);
					
					
		%>
<table border="1" cellspacing="10" cellpadding="1" width="80%" align="center">
<tr bgcolor="9F3C8A">
	<TH>Book Id</TH><TH>Title</TH><TH>Author(s)</TH><TH>Branch Id</TH><TH>Total copies</TH><TH>Available</TH>
</tr>			
			<% 
			if (searchResultList != null) {
				System.out.println(searchResultList.size());%>

				<%for (SearchResult_New searchResult : searchResultList) { %>
					
					<% HashMap<Integer, BranchAvailability> branchInfo = searchResult
							.getBranchInfo();
					Set<Integer> branchIDSet = branchInfo.keySet();
					Iterator<Integer> iterator = branchIDSet.iterator();
					
					 while (iterator.hasNext()) { %>
<tr>					 
					 <%
						BranchAvailability branchAvailability = branchInfo.get(iterator.next());
						System.out.println(searchResult.getBook_id() + "#"+ searchResult.getTitle() + "***"+ searchResult.getAuthors() + "***"+ branchAvailability.getBranch_id() + "~~"+ branchAvailability.getNo_of_copies()+ "~~"+ branchAvailability.getAvailable_copies());
						%>
<!-- Create the table cell display here -->				
								<td><%=searchResult.getBook_id() %></td>
								<td><%=searchResult.getTitle() %></td>
								<td><%=searchResult.getAuthors() %></td>
								<td align="center"><%=branchAvailability.getBranch_id() %></td>
								<td align="center"><%=branchAvailability.getNo_of_copies() %></td>
								<td align="center"><%=branchAvailability.getAvailable_copies() %></td>
<!-- End of table cell display here -->									
</tr>								
				<% 	}//end while %>
					 
			<%	}//end for %>
			<% }//end if %>
</table>
<form >

</form>
		<%	
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception");
		}
	%>
</body>
</html>