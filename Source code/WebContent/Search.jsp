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
	font-size: 14pt;
	color: black;
}
</style>
<script language="JavaScript" type="text/javascript">
	function checkform(form) {
		var retVal = true;

		if ((form.bookIdInput.value == "" && form.titleInput.value == ""
				&& form.authorNameInput.value == "") && (form.bookIdInput.value == "" && form.titleInput.value == ""
					&& form.firstNameInput.value == "" && form.middleNameInput.value == "" && form.lastNameInput.value == "")) {
			document.getElementById("p1").innerHTML = "All Fields Can't be empty.";
			retVal = false;
		}

		return retVal;
	}
	function disableFullName() {
		if (document.getElementById("IdpartialRadio").checked == 1) {
			document.getElementById("IdauthorNameInput").disabled = true;
			document.getElementById("IdauthorNameInput").style.backgroundColor = '#C0C0C0';
			document.getElementById("IdauthorNameInput").value = "";
			document.getElementById("IdfirstNameInput").disabled = false;
			document.getElementById("IdfirstNameInput").style.backgroundColor = 'white';
			document.getElementById("IdfirstNameInput").value = "";
			document.getElementById("IdmiddleNameInput").disabled = false;
			document.getElementById("IdmiddleNameInput").style.backgroundColor = 'white';
			document.getElementById("IdmiddleNameInput").value = "";
			document.getElementById("IdlastNameInput").disabled = false;
			document.getElementById("IdlastNameInput").style.backgroundColor = 'white';
			document.getElementById("IdlastNameInput").value = "";
		} else {
		}
	}
	function disablePartialName() {
		if (document.getElementById("IdfullRadio").checked == 1) {
			document.getElementById("IdfirstNameInput").disabled = true;
			document.getElementById("IdfirstNameInput").style.backgroundColor = '#C0C0C0';
			document.getElementById("IdfirstNameInput").value = "";
			document.getElementById("IdmiddleNameInput").disabled = true;
			document.getElementById("IdmiddleNameInput").style.backgroundColor = '#C0C0C0';
			document.getElementById("IdmiddleNameInput").value = "";
			document.getElementById("IdlastNameInput").disabled = true;
			document.getElementById("IdlastNameInput").style.backgroundColor = '#C0C0C0';
			document.getElementById("IdlastNameInput").value = "";
			document.getElementById("IdauthorNameInput").disabled = false;
			document.getElementById("IdauthorNameInput").style.backgroundColor = 'white';
			document.getElementById("IdauthorNameInput").value = "";
		} else {

		}
	}
</script>
</head>
<body>

	<p align="right">
		<input type="button" value="Back"
			onclick="window.location='Home.jsp';" />&nbsp &nbsp <input
			type="button" value="Home" onclick="window.location='Home.jsp';" /><br>

	</p>
	<h1 class="header" align="center">Library Management System</h1>



	<h1 class="text" align="center">Enter any combination of Book Id,
		Book Title and Author Name to locate a book.</h1>
	<br>
	<form action="SearchResult.jsp" onsubmit="return checkform(this);">


		<table align="center">
			<tr>
				<td align="right">&nbsp &nbsp &nbsp &nbsp</td>
				<td align="right">Book Id :&nbsp &nbsp</td>
				<td align="left"><input type="text" name="bookIdInput" value="">
				</td>
			</tr>
			<tr>
				<td align="right">&nbsp &nbsp &nbsp &nbsp</td>
				<td align="right">Title :&nbsp &nbsp</td>
				<td><input type="text" name="titleInput" value=""></td>

			</tr>

			<tr>
				<td align="right"><input type="radio" name="name"
					id="IdfullRadio" checked="checked" value="full"
					onChange="disablePartialName();"></td>
				<td align="right">Author Name :&nbsp &nbsp</td>
				<td align="left"><input type="text" name="authorNameInput"
					id="IdauthorNameInput" value=""></td>
			</tr>
			<tr>
				<td align="right"><input type="radio" name="name"
					id="IdpartialRadio" value="partial" onChange="disableFullName();"></td>
				<td align="right">First Name :&nbsp &nbsp</td>
				<td align="left"><input type="text" name="firstNameInput"
					id="IdfirstNameInput" disabled="true" value=""
					STYLE="background-color: #C0C0C0;"></td>
			</tr>
			<tr>
				<td align="right">&nbsp &nbsp &nbsp &nbsp</td>
				<td align="right">Middle Name :&nbsp &nbsp</td>
				<td align="left"><input type="text" name="middleNameInput"
					id="IdmiddleNameInput" disabled="true" value=""
					STYLE="background-color: #C0C0C0;"></td>
			</tr>
			<tr>
				<td align="right">&nbsp &nbsp &nbsp &nbsp</td>
				<td align="right">Last Name :&nbsp &nbsp</td>
				<td align="left"><input type="text" name="lastNameInput"
					id="IdlastNameInput" disabled="true" value=""
					STYLE="background-color: #C0C0C0;"></td>
			</tr>
			<tr>
				<td align="right">&nbsp &nbsp &nbsp &nbsp</td>
				<td align="right">&nbsp &nbsp &nbsp &nbsp</td>
				<td align="center"><input type="submit" name="submitSearch"
					value="Search"></td>

			</tr>
			<tr>
			<tr>
				<td align="right">&nbsp &nbsp &nbsp &nbsp</td>
				<td align="right">&nbsp &nbsp &nbsp &nbsp</td>
				<td align="center"><p id="p1">
					<h2 style="color: #6A0C07">&nbsp</h2>
					</p></td>
				

			</tr>
		</table>
		<br>

	</form>





</body>
</html>