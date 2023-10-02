package utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;


public class FetchValueFromDB 
{	
	public String fetchSingleValuefromDB(String query, String Column_name) throws StreamWriteException, DatabindException, IOException
	{
		ReadPropertiesfile re = new ReadPropertiesfile();
		System.out.println(re.get_SQL_Link());
		System.out.println(re.get_SQL_UserName());
		System.out.println(re.get_SQL_Password());
		ResultSet resultSet = null;
		 String value =null;
		//below object will use when query returning only one record and will create one json file
		
		try {
			Connection connection = 
					DriverManager.getConnection(re.get_SQL_Link(),re.get_SQL_UserName(),re.get_SQL_Password());
			System.out.println("Coonected to MSSQL server");
			Statement statement = connection.createStatement();
			System.out.println(query);
			System.out.println(Column_name);
					
			resultSet = statement.executeQuery(query);
			
				while (resultSet.next()) {
	                 value = resultSet.getString(Column_name);
	                System.out.println("Value: " + value);
	            }
			
		}catch(SQLException e)
		{
			System.out.println("error while SQL connectivity");
			e.printStackTrace();
		}
		return value;		
	}
	
}
