$(document).ready(function() 
{  
		$("#alertSuccess").hide();  
	    $("#alertError").hide(); 
}); 
 
 
// SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 
 
	// Form validation-------------------  
	var status = validateCustomerForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 
 
	// If valid------------------------  
	var type = ($("#hidCustomerIDSave").val() == "") ? "POST" : "PUT"; 

	$.ajax( 
	{  
			url : "CustomerService",  
			type : type,  
			data : $("#formCustomer").serialize(),  
			dataType : "text",  
			complete : function(response, status)  
			{   
				onCustomerSaveComplete(response.responseText, status);  
			} 
	}); 
}); 


function onCustomerSaveComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully saved.");    
			$("#alertSuccess").show(); 

			$("#divCustomerGrid").html(resultSet.data);   
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		} 

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while saving.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while saving..");   
		$("#alertError").show();  
	} 

	$("#hidCustomerIDSave").val("");  
	$("#formCustomer")[0].reset(); 
} 

//UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
{     
	$("#hidCustomerIDSave").val($(this).closest("tr").find('#hidCustomerIDUpdate').val());     
	$("#cName").val($(this).closest("tr").find('td:eq(0)').text());     
	$("#cAddress").val($(this).closest("tr").find('td:eq(1)').text());
	$("#cEmail").val($(this).closest("tr").find('td:eq(2)').text());
	$("#cPhone").val($(this).closest("tr").find('td:eq(3)').text());     
}); 

//REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) 
{  
	$.ajax(  
	{   
		url : "CustomerService",   
		type : "DELETE",   
		data : "cID=" + $(this).data("customerid"),   
		dataType : "text",   
		complete : function(response, status)   
		{    
			onCustomerDeleteComplete(response.responseText, status);   
		}  
	}); 
}); 

function onCustomerDeleteComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			
			$("#alertSuccess").text("Successfully deleted.");    
			$("#alertSuccess").show(); 
		
			$("#divCustomerGrid").html(resultSet.data); 
			
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		}
		

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while deleting.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while deleting..");   
		$("#alertError").show();  
	}
}

//CLIENT-MODEL========================================================================= 
function validateCustomerForm() 
{  
	// NAME  
	if ($("#cName").val().trim() == "")  
	{   
		return "Insert Name.";  
	} 

	// ADDRESS------------------------  
	if ($("#cAddress").val().trim() == "")  
	{   
		return "Insert Address.";  
	} 
	
	// EMAIL------------------------  
	if ($("#cEmail").val().trim() == "")  
	{   
		return "Insert Email.";  
	} 
	
	
	//PHONE-------------------------------
	 var tmpPhone = $("#cPhone").val().trim();
	if (!$.isNumeric(tmpPhone)) 
	 {
	 return "Insert Phone No.";
	 }

	return true; 
}
