<%@ page import="com.Customer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customer Service</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="Components/jquery-3.4.1.min.js"></script> 
<script src="Components/valicustomer.js"></script> 
</head>
<body>
<div class="container"> 
	<div class="row">  
		<div class="col-6"> 
			<h1>Customer Registration</h1>
				<form id="formCustomer" name="formCustomer" method="post" action="Index.jsp">  
					Customer Name:  
 	 				<input id="cName" name="cName" type="text"  class="form-control form-control-sm">
					<br>Address:   
  					<input id="cAddress" name="cAddress" type="text" class="form-control form-control-sm">   
  					<br>Email:   
  					<input id="cEmail" name="cEmail" type="text"  class="form-control form-control-sm">
					<br>Phone No:   
  					<input id="cPhone" name="cPhone" type="text"  class="form-control form-control-sm">
					<br>  
					<input id="btnSave" name="btnSave" type="button" value="SAVE" class="btn btn-primary">  
					<input type="hidden" id="hidCustomerIDSave" name="hidCustomerIDSave" value=""> 
				</form>
				
				<div id="alertSuccess" class="alert alert-success"> </div>
				
			   <div id="alertError" class="alert alert-danger"></div>
				
			   <br>
				<div id="divCustomerGrid">
					<%
						Customer customerObj = new Customer();
						out.print(customerObj.readCustomer());
					%>
				</div>
				
				 
			</div>
		</div>
</div>
 
</body>
</html>