package LetsShop;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.annotations.Test;

import core.StatusCode;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.ReadExcelFile;
import utils.ReadPropertiesfile;
public class LoginAndGenerateToken {
	ReadPropertiesfile readvalue = new ReadPropertiesfile();
	ReadExcelFile readex = new ReadExcelFile();	
	ArrayList<String> list = new ArrayList<String>();

	
	public String[] getResponseFromLetsShopLogin()  {
		RestAssured.baseURI = readvalue.getBaseUrlLetsShopforQA();
		String[] loginresponce = new String[2];
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
		return loginresponce ;
	}
	@Test
	public void verifyInvalidLogin() throws IOException  {
		RestAssured.baseURI = readvalue.getBaseUrlLetsShopforQA();
		list =readex.readexceldatafile("Verify_Invalid_LOgin");
		for(int i=0;i<list.size();i++)
		{
		Response response = given().header("Content-Type", "Application/json")
				// .body(IOUtils.toString(ReadJsonFile.readanyjsonfile("letsShopUserdetails.json")))
				.body("{\"userEmail\":\"" + list.get(i) + "\",\"userPassword\":\""
						+ list.get(++i) + "\"}")
				.when().post("/auth/login");
		int actualstatuscode = response.getStatusCode();
		assertEquals(actualstatuscode, Integer.parseInt(list.get(++i)));
		response.then().body(list.get(++i), is(list.get(++i)));
		System.out.println(response.asString());	
		}
	}
}
