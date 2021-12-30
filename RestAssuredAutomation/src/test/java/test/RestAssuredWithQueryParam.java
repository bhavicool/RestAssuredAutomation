package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestAssuredWithQueryParam {
	
	@Test
	public void testGet() throws IOException
	{
		RestAssured.baseURI=getURLDetails("apiBaseURI");
		Response response=RestAssured.given()
				.queryParam("page",2)
				.get(RestAssured.baseURI);
		
		System.out.println("Response Code is:"+response.getStatusCode());
		System.out.println("Response Body is:"+response.getBody().asString());
		
		
	}
	
	public static String getURLDetails(String key) throws IOException
	{
		Properties prop = new Properties();
		String fileName = "\\RestAssuredTesting\\RestAssuredAutomation\\src\\test\\resources\\utils\\data.config";
		try (FileInputStream fis = new FileInputStream(fileName)) {
			prop.load(fis);
		} catch (FileNotFoundException ex) {

		} 
		return prop.getProperty(key);
	}

}
