package LetsShop;

import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.relevantcodes.extentreports.LogStatus;

import core.Base;
import core.StatusCode;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import utils.ExtentReport;
import utils.ReadExcelFile;
import utils.ReadJsonFile;
import utils.ReadPropertiesfile;

public class AddProduct extends Base  {
	
	ReadPropertiesfile readvalue = new ReadPropertiesfile();
	LoginAndGenerateToken td = new LoginAndGenerateToken();
	ReadExcelFile readex = new ReadExcelFile();
	ArrayList<String> list = new ArrayList<String>();
	public static ArrayList<String> storeproductid = new ArrayList<String>();
	
	
	// below method adding single product using hard code values
	
	public void addProduct() {
		File file = new File("resources\\Images\\shirt.png");
		File file1 = new File("resources\\TestData\\lecture.txt");
		
		RestAssured.baseURI = readvalue.getBaseUrlLetsShopforQA();
		Response response = given().log().all().header("Content-Type", "multipart/form-data")
				.header("Authorization", td.getResponseFromLetsShopLogin()[0])
				.formParam("productName", "ABC")
				.formParam("productAddedBy", td.getResponseFromLetsShopLogin()[1])
				.formParam("productCategory", "fghgfh").formParam("productSubCategory", "shirts")
				.formParam("productPrice", "567").formParam("productDescription", "fhggfhf")
				.formParam("productFor", "Men").multiPart("productImage", file1).when().post("/product/add-product");
		assertEquals(response.statusCode(), 500);
		System.out.println(response.asString());
	}
	
	// below method adding single product using Json file and do the changes in json file while adding product
	
	public void addProductUsingJsonInputdata() throws StreamReadException, DatabindException, IOException {
		File file1 = new File(ReadJsonFile.readAddProductJson().get(6));
		Map<String, String> body = new HashMap<String, String>();
		body.put("productName", ReadJsonFile.readAddProductJson().get(0));
		body.put("productCategory", ReadJsonFile.readAddProductJson().get(1));
		body.put("productSubCategory", ReadJsonFile.readAddProductJson().get(2));
		body.put("productPrice", ReadJsonFile.readAddProductJson().get(3));
		body.put("productDescription", ReadJsonFile.readAddProductJson().get(4));
		body.put("productFor", ReadJsonFile.readAddProductJson().get(5));

		RestAssured.baseURI = readvalue.getBaseUrlLetsShopforQA();
		Response response = given().log().all().header("Content-Type", "multipart/form-data")
				.header("Authorization", td.getResponseFromLetsShopLogin()[0])
				.formParam("productAddedBy", td.getResponseFromLetsShopLogin()[1]).formParams(body)
				.multiPart("productImage", file1).when().post("/product/add-product");
		assertEquals(response.statusCode(), StatusCode.CREATED.code);
		System.out.println(response.asString());
	}
	
	/*below method adding multiple product using excel file 
	and do the changes in excel file while adding product and login executed each time add product*/
	
	
	public void addProductUsingExcelData() throws IOException
	{	RestAssured.baseURI = readvalue.getBaseUrlLetsShopforQA();
		list =readex.readexceldatafile("Add_Products");
		for(int i=0;i<list.size();i++)
		{ 
			File file = new File(list.get(i));
			
			Response response = given().log().all().header("Content-Type", "multipart/form-data")
					.header("Authorization", td.getResponseFromLetsShopLogin()[0])
					.formParam("productName", list.get(++i))
					.formParam("productAddedBy", td.getResponseFromLetsShopLogin()[1])
					.formParam("productCategory", list.get(++i))
					.formParam("productSubCategory", list.get(++i))
					.formParam("productPrice", list.get(++i))
					.formParam("productDescription", list.get(++i))
					.formParam("productFor", list.get(++i))
					.multiPart("productImage", file)
					.when().post("/product/add-product");
			assertEquals(response.statusCode(), Integer.parseInt(list.get(++i)));
			response.then().body(list.get(++i), is(list.get(++i)));
			System.out.println(response.asString());		

		}
	}	
	/*below method adding multiple product using excel file 
	and do the changes in excel file while adding product login executed only one time using
	Singleton concept and login method executed only once */
	@Test
	public void addProductUsingSingletonlogin() throws IOException
	{	try {
		extenttest=extentreport.startTest("addProductUsingSingletonlogin","Add Product");
		RestAssured.baseURI = readvalue.getBaseUrlLetsShopforQA();
		list =readex.readexceldatafile("Add_Products");
		for(int i=0;i<list.size();i++)
		{ 
			SingletonLogin generator = SingletonLogin.getInstance();
			
			File file = new File(list.get(i));			
			Response response = given().log().all().header("Content-Type", "multipart/form-data")
					.header("Authorization", generator.getLoginDetails()[0])
					.formParam("productName", list.get(++i))
					.formParam("productAddedBy", generator.getLoginDetails()[1])
					.formParam("productCategory", list.get(++i))
					.formParam("productSubCategory", list.get(++i))
					.formParam("productPrice", list.get(++i))
					.formParam("productDescription", list.get(++i))
					.formParam("productFor", list.get(++i))
					.multiPart("productImage", file)
					.when().post("/product/add-product");
			assertEquals(response.statusCode(), Integer.parseInt(list.get(++i)));
			response.then().body(list.get(++i), is(list.get(++i)));
			System.out.println(response.asString());
			extenttest.log(LogStatus.INFO, response.asString());
			int status_code_responce = response.getStatusCode();			
			if(status_code_responce==201)
			{
				System.out.println("Product added sucessfully");
				storeproductid.add((String) response.path("productId"));				
			}			
		}
		System.out.println("Total Product added"+storeproductid.size());
		for(int j=0;j<storeproductid.size();j++)
		{
			System.out.println("Product ID"+j+"Product name Is "+storeproductid.get(j));
			extenttest.log(LogStatus.INFO,"Product ID"+storeproductid.get(j));
		}
	}
	catch(Exception e)
	{
		extenttest.log(LogStatus.ERROR,e.getMessage());
	}
	}	
	
}
