package LetsShop;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import core.Base;
import core.StatusCode;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.ExtentReport;
import utils.ReadPropertiesfile;

public class DeleteProducts extends Base {
	ReadPropertiesfile readvalue = new ReadPropertiesfile();
	LoginAndGenerateToken td = new LoginAndGenerateToken();

	public void deleteProductusingID() throws IOException {
		System.out.println("Token from array : " + td.getResponseFromLetsShopLogin()[0]);
		RestAssured.baseURI = readvalue.getBaseUrlLetsShopforQA();
		Response response = given().log().all().header("Content-Type", "Application/json")
				.header("Authorization", td.getResponseFromLetsShopLogin()[0]).when()
				.delete("product/delete-product/651020f47244490f95b800c8");
		assertEquals(response.statusCode(), StatusCode.SUCCESS.code);
		response.then().body("message", is("Product Deleted Successfully"));
		System.out.println(response.asString());
	}

	@Test
	public void deleteProductusingIDAndSingltoneLogin() throws IOException

	{
		try {
			extenttest = extentreport.startTest("deleteProductusingIDAndSingltoneLogin", "Delete the added Product");

			SingletonLogin generator = SingletonLogin.getInstance();
			RestAssured.baseURI = readvalue.getBaseUrlLetsShopforQA();
			for (int i = 0; i < AddProduct.storeproductid.size(); i++) {
				Response response = given().log().all().header("Content-Type", "Application/json")
						.header("Authorization", generator.getLoginDetails()[0]).when()
						.delete("product/delete-product/" + AddProduct.storeproductid.get(i));
				assertEquals(response.statusCode(), StatusCode.SUCCESS.code);
				response.then().body("message", is("Product Deleted Successfully"));
				System.out.println(response.asString());
				extenttest.log(LogStatus.INFO, response.asString());
				extenttest.log(LogStatus.INFO, "Product ID" + AddProduct.storeproductid.get(i));
			}
		} catch (Exception e) {
			extenttest.log(LogStatus.ERROR, e.getMessage());
		}
	}
}
