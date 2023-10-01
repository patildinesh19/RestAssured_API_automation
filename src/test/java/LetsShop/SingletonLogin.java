package LetsShop;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import core.StatusCode;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.ReadPropertiesfile;

public class SingletonLogin 
{
	private static SingletonLogin instance =null;
	private String[] loginresponce = new String[2];
	ReadPropertiesfile readvalue = new ReadPropertiesfile();
	
	private SingletonLogin()
	{
		RestAssured.baseURI = readvalue.getBaseUrlLetsShopforQA();
		Response response = given().header("Content-Type", "Application/json")
				// .body(IOUtils.toString(ReadJsonFile.readanyjsonfile("letsShopUserdetails.json")))
				.body("{\"userEmail\":\"" + readvalue.getLetsShopUsername_QA() + "\",\"userPassword\":\""
						+ readvalue.getLetsShopPassword_QA() + "\"}")
				.when().post("/auth/login");
		int actualstatuscode = response.getStatusCode();
		assertEquals(actualstatuscode, StatusCode.SUCCESS.code);
		System.out.println(actualstatuscode);
		System.out.println(response.asString());
		loginresponce[0] = response.path("token");
		loginresponce[1] = response.path("userId");		
	}
	public static SingletonLogin getInstance()
	{
		if(instance == null)
		{
			instance= new SingletonLogin();
		}
		return instance;		
	}
	public String[] getLoginDetails()
	{
		return loginresponce;
		
	}
}
