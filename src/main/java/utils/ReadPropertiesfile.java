package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadPropertiesfile {
	private InputStream strem = null;
	private Properties pr = null;

	public ReadPropertiesfile() {
		try {
			pr = new Properties();
			strem = new FileInputStream(System.getProperty("user.dir") + "\\Config\\config.properites");
			try {
				pr.load(strem);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String getBaseUrl() {
		return pr.getProperty("base_url");
	}

	public String getAPI_Key() {
		return pr.getProperty("API_KEY");
	}

	public String getBrowser() {
		return pr.getProperty("browser");
	}

	public String getBaseUrlLetsShopforQA() {

		return pr.getProperty("QA_baseurl_Lets_Shop");
	}

	public String getBaseUrlFOrBookDetails() {

		return pr.getProperty("Book_details_base_url");
	}
	
	public String getLetsShopUsername_QA()
	{
		return pr.getProperty("QA_Username_Lets_Shop");
	}
	public String getLetsShopPassword_QA()
	{
		return pr.getProperty("QA_Password_Lets_Shop");
	}
	public String get_API_TestData_From_Excel_File()
	{
		return pr.getProperty("Excel_Sheet_Name");
	}
	public String get_json_schema_file_for_get_all_product()
	{
		return pr.getProperty("Json_schema_File_for_all_get_Product");
	}
	public String get_SQL_Link()
	{
		return pr.getProperty("SQL_Server_Connect_link");
	}
	public String get_SQL_UserName()
	{
		return pr.getProperty("SQL_Server_UserName");
	}
	public String get_SQL_Password()
	{
		return pr.getProperty("SQL_Server_Password");
	}
}
