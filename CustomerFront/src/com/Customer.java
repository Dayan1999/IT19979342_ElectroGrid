package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


public class Customer {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertCustomer(String cName, String cAddress, String cEmail, String cPhone)  
	{   
		String output = ""; 	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for inserting."; } 
	 
			// create a prepared statement 
			String query = " insert into customer(`cID`,`cName`,`cAddress`,`cEmail`,`cPhone`)" + " values (?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, cName);
			 preparedStmt.setString(3, cAddress);
			 preparedStmt.setString(4, cEmail);
			 preparedStmt.setString(5, cPhone);
			
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	   
			String newCustomer = readCustomer(); 
			output =  "{\"status\":\"success\", \"data\": \"" + newCustomer + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the Customer.\"}";  
			System.err.println(e.getMessage());   
		} 		
	  return output;  
	} 	

	public String readCustomer()  
	{   
		String output = ""; 
		try   
		{    
			Connection con = connect(); 
		
			if (con == null)    
			{
				return "Error while connecting to the database for reading."; 
			} 
	 
			// Prepare the html table to be displayed    
			output = "<table border=\'1\'><tr><th>Customer</th><th>Address</th><th>Email</th><th>Phone No</th><th>Update</th><th>Remove</th></tr>";
	 
			String query = "select * from customer";    
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
	 
			// iterate through the rows in the result set    
			while (rs.next())    
			{     
				String cID = Integer.toString(rs.getInt("cID"));
				 String cName = rs.getString("cName");
				 String cAddress = rs.getString("cAddress");
				 String cEmail = rs.getString("cEmail");
				 String cPhone = rs.getString("cPhone");
				 
				// Add into the html table 
				output += "<tr><td><input id=\'hidCustomerIDUpdate\' name=\'hidCustomerIDUpdate\' type=\'hidden\' value=\'" + cID + "'>" 
							+ cName + "</td>"; 
				output += "<td>" + cAddress + "</td>";
				output += "<td>" + cEmail + "</td>";
				output += "<td>" + cPhone + "</td>";
	 
				// buttons     
				output +="<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"       
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-customerid='" + cID + "'>" + "</td></tr>"; 
			
			}
			con.close(); 
	   
			output += "</table>";   
		}   
		catch (Exception e)   
		{    
			output = "Error while reading the Customer.";    
			System.err.println(e.getMessage());   
		} 	 
		return output;  
	}
	public String updateCustomer(String cID, String cName, String cAddress, String cEmail, String cPhone)  
	{   
		String output = "";  
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for updating."; } 
	 
			// create a prepared statement    
			String query = "UPDATE customer SET cName=?,cAddress=?,cEmail=?,cPhone=?"  + "WHERE cID=?";  	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setString(1, cName);
			 preparedStmt.setString(2, cAddress);
			 preparedStmt.setString(3, cEmail);
			 preparedStmt.setString(4, cPhone);
			 preparedStmt.setInt(5, Integer.parseInt(cID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close();  
			String newCustomer = readCustomer();    
			output = "{\"status\":\"success\", \"data\": \"" + newCustomer + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while updating the Customer.\"}";   
			System.err.println(e.getMessage());   
		} 	 
	  return output;  
	} 
	public String deleteCustomer(String cID)   
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{
				return "Error while connecting to the database for deleting."; 			
			} 
	 
			// create a prepared statement    
			String query = "delete from customer where cID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, Integer.parseInt(cID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newCustomer = readCustomer();    
			output = "{\"status\":\"success\", \"data\": \"" +  newCustomer + "\"}";    
		}   
		catch (Exception e)   
		{    
			output = "Error while deleting the Customer.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	
}