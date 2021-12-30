package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.UserData;

public class RestAssuredPojoTest {

	@org.junit.Test
	public void testResponseCodeFromPostWithFile() throws IOException
	{
		//We are using RequestSpecification interface because we will send request in below format
		//given() means start building the request
		RestAssured.baseURI=getURLDetails("registerAPI");

		RequestSpecification request=RestAssured.given();
		File file = new File("\\RestAssuredTesting\\RestAssuredAutomation\\src\\test\\resources\\utils\\Input.json");
		request.header("Content-Type","application/json").body(file);

		Response response=request.post(RestAssured.baseURI);
		System.out.println("Status of POST with file call is:"+response.getStatusCode());
		Assert.assertEquals("200",String.valueOf(response.getStatusCode()));
		System.out.println("Response Body is:"+response.getBody().asString());

		UserData userData=response.getBody().as(UserData.class);
		System.out.println("Token Value is:"+userData.getToken());
		System.out.println("Id is:"+userData.getId());
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
