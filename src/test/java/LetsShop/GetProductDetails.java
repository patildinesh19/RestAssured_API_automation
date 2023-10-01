package LetsShop;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import core.Base;
import core.StatusCode;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import pojo.Letsshop.GetProductDetailsPojo;
import utils.ExtentReport;
import utils.ReadPropertiesfile;

public class GetProductDetails extends Base {
	ReadPropertiesfile readvalue = new ReadPropertiesfile();
	LoginAndGenerateToken td = new LoginAndGenerateToken();

	@Test
	public void verifyAddedProductUsingSingaletonLogin() {
		try {
			extenttest = extentreport.startTest("verifyAddedProductUsingSingaletonLogin", "Verify Added Product");

			RestAssured.baseURI = readvalue.getBaseUrlLetsShopforQA();
			SingletonLogin generator = SingletonLogin.getInstance();

			Response response = given().log().all().header("Content-Type", "Application/json")
					.header("Authorization", generator.getLoginDetails()[0]).when().post("/product/get-all-products");
			assertEquals(response.statusCode(), StatusCode.SUCCESS.code);
			response.then().body("count", is(5));
			System.out.println(response.asString());
			extenttest.log(LogStatus.INFO, response.asString());
			GetProductDetailsPojo responseBody = response.as(GetProductDetailsPojo.class);
			System.out.println("Total number of Product =" + responseBody.getCount());
			System.out.println("Addded 4th Product is " + responseBody.getData().get(4).get_id());
			for (int i = 0; i < responseBody.getData().size(); i++) {
				for (int j = 0; j < AddProduct.storeproductid.size(); j++)
					if (responseBody.getData().get(i).get_id().equalsIgnoreCase(AddProduct.storeproductid.get(j))) {
						System.out.println("Product Added sucessfully" + AddProduct.storeproductid.get(j));
						extenttest.log(LogStatus.INFO, "Product Name : " + responseBody.getData().get(i).getProductName());
					}
			}
		} catch (Exception e) {
			extenttest.log(LogStatus.ERROR, e.getMessage());
		}
		
	}

	public void verifyHomePageShowAllProducts() throws IOException {
		RestAssured.baseURI = readvalue.getBaseUrlLetsShopforQA();
		Response response = given().log().all().header("Content-Type", "Application/json")
				.header("Authorization", td.getResponseFromLetsShopLogin()[0]).when().post("/product/get-all-products");
		assertEquals(response.statusCode(), StatusCode.SUCCESS.code);
		response.then().body("count", is(5));
		System.out.println(response.asString());
		GetProductDetailsPojo responseBody = response.as(GetProductDetailsPojo.class);

		System.out.println("Total number of Product =" + responseBody.getCount());
		System.out.println("Addded 4th Product is " + responseBody.getData().get(4).get_id());
	}
	@Test
	public void validateJsonSchemaForGetAllProduct()
	{	File Schema = new File(readvalue.get_json_schema_file_for_get_all_product());
		extenttest = extentreport.startTest("validateJsonSchemaForGetAllProduct", "Verify Json schema for getproduct service");

		RestAssured.baseURI = readvalue.getBaseUrlLetsShopforQA();
		SingletonLogin generator = SingletonLogin.getInstance();

		 given().log().all().header("Content-Type", "Application/json")
				.header("Authorization", generator.getLoginDetails()[0])
				.when()
				.post("/product/get-all-products")
				.then()
				.assertThat()
				.statusCode(200)
				.body(JsonSchemaValidator.matchesJsonSchema(Schema));
	}
}

