package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pojo.Letsshop.AddProduct;
import pojo.createpojoforcustomer_db.CustomerFromDB;
import pojo.petspojs.PetsDetails;

public class ReadJsonFile 
{ 
	 
	@Test
	public static void readcustomerTestDateFromDBJson() throws StreamReadException, DatabindException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		CustomerFromDB cs = mapper.readValue(new File("Resources\\TestData\\customer_test_data_from_db.json"),
				CustomerFromDB.class);
		System.out.println(cs.getCustomername());		
	}
	@Test
	public static void readPetsDetailsJson() throws StreamReadException, DatabindException, IOException
	{	ObjectMapper mapper = new ObjectMapper();
		List<PetsDetails> PetsDetailslist = null;
		PetsDetailslist = mapper.readValue
				(new File("Resources\\TestData\\TestData.json"), new TypeReference<List<PetsDetails>>(){});
		System.out.println(PetsDetailslist.get(0).getId());
		System.out.println(PetsDetailslist.get(0).getCategory().getName());
		System.out.println(PetsDetailslist.get(0).getTags().get(0).getName());	    
	}
	
	public static List<String> readAddProductJson() throws StreamReadException, DatabindException, IOException
	{	ObjectMapper mapper = new ObjectMapper();
		AddProduct addproduct = mapper.readValue(new File("Resources\\TestData\\addproduct.json"), 
				AddProduct.class);
		List<String> list = new ArrayList<String>();
		list.add(addproduct.getProductName().toString());
		list.add(addproduct.getProductCategory().toString());
		list.add(addproduct.getProductSubCategory().toString());
		list.add(addproduct.getProductPrice().toString());
		list.add(addproduct.getProductDescription().toString());
		list.add(addproduct.getProductFor().toString());
		list.add(addproduct.getProductImage().toString());
		return list;
	}	
}
