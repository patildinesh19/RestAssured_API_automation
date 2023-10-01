package utils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pojo.createpojoforcustomer_db.CustomerFromDB;

/* Class is to connect Database and create json file */

public class CoonectToDB {
	private static String connectionurl = "jdbc:sqlserver://LAPTOP-VN1CB8H2\\QA.database.windows.net:1433;"
			+ "database=Employee_Managment;" + "encrypt=true;" + "trustServerCertificate=true;" + "loginTimeout=30;";
	private static  String username = "sa";
	private static  String Password = "DD123@dd123";
	
	@Test
public static void connectdb() throws StreamWriteException, DatabindException, IOException
{
	custinoftable();
	
}
	public static void custinoftable() throws StreamWriteException, DatabindException, IOException
	{
		ResultSet resultSet = null;
		//below object will use when query returning only one record and will create one json file
		CustomerFromDB cs= new CustomerFromDB();
		
		try {
			Connection connection = DriverManager.getConnection(connectionurl, username, Password);
			System.out.println("Coonected to MSSQL server");
			Statement statment = connection.createStatement();
			String selectquery = "select TOP 1 * from CustomerInfo";
			resultSet = statment.executeQuery(selectquery);
			while (resultSet.next()) {
				
				cs.setCustomerID(resultSet.getInt(1));
				cs.setCustomername(resultSet.getString(2));
				cs.setPurchesdate(resultSet.getString(3));
				cs.setAmount(resultSet.getInt(4));
				cs.setLocation(resultSet.getString(5));				
			}
			
			System.out.println("CUSTOMER id: "+cs.getCustomerID());
			System.out.println("CUSTOMER NAME: "+cs.getCustomername());
			System.out.println("PURCHES DARE: "+cs.getCustomername());
			System.out.println("AMOUNT : "+cs.getAmount());
			System.out.println("lOCATION: "+cs.getLocation());	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error while SQL connectivity");
			e.printStackTrace();
		}
		ObjectMapper o = new ObjectMapper();
		o.writeValue(new File("Resources\\TestData\\customer_test_data_from_db.json"),cs);
	}

}
