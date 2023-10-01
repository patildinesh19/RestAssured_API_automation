package peatesapplication;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;


import org.testng.annotations.Test;

import core.StatusCode;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utils.ReadPropertiesfile;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class Verifypeatasperstatus
{
	ReadPropertiesfile readvalue = new ReadPropertiesfile();
	
	@Test
	public void getpeatsdetails()
	{	
		RestAssured.baseURI =readvalue.getBaseUrl();
	System.out.println("BASE_URL: "+readvalue.getBaseUrl());
	given()	
	.queryParam("status", "sold")
		.when().get("/pet/findByStatus")
		.then().assertThat()
		.statusCode(200)
		 .body(not(isEmptyString()))
         .body("[1].category.name", equalTo("Lana"));
         
		
	}
	@Test
	public void getpeatsdetails1() 
	{	RestAssured.baseURI ="https://petstore3.swagger.io/api/v3";
	Response response =given()	
			.header("Content-Type","Application/json")
			.queryParam("status", "sold")
		.when().get("/pet/findByStatus");
		int actualstatuscode =response.getStatusCode();
		
		assertEquals(actualstatuscode, StatusCode.SUCCESS.code);
		assertThat(response.jsonPath().getList(""), hasSize(3));
		assertThat(response.jsonPath().getList("[0].tags"), hasSize(2));
		
		//assertThat(response.jsonPath().getList("[0].name"), is("Dog 2"));
		
		System.out.println("0");
		//another way
		response.then().body("", hasSize(3));
		
		response.then().body("[0].tags", hasSize(2));
		
		response.then().body("[0].category.name", is("Dogs"));
		
		System.out.println(response.body().prettyPrint());
		
		
		Headers headeresObj = response.getHeaders();
		for(Header h :headeresObj )
		{
			System.out.println(h.getName() +"  "+h.getValue());
			if(h.getName().equalsIgnoreCase("Server"))
			{
				System.out.println(h.getValue());
				assertEquals(h.getValue(), "Jetty(9.4.9.v20180320)");
			}
		}
				
	}
}


