package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredHTTPMethods {

	@org.junit.Test
	public void testResponseCodeFromGet()
	{

		//Response is an interface and RestAssured is a class
		//get is a static method of RestAssuredClass
		Response resp=
				RestAssured.get("https://reqres.in/api/users/5");
		int code=resp.getStatusCode();
		System.out.println("Response Code From GET is:"+code);
		System.out.println("Response Body is:"+resp.getBody().asString());
		System.out.println("Response Time is:"+resp.getTime());
		Assert.assertEquals("200",String.valueOf(code));
	}

	@org.junit.Test
	public void testResponseCodeFromPost()
	{
		//We are using RequestSpecification interface because we will send request in below format
		RequestSpecification request=RestAssured.given();
		request.header("Content-Type","application/json");
		//JsonObject is a class which will help to add data in JSON format to Rest API
		//public class JSONObject extends HashMap implements Map
		JSONObject json=new JSONObject();
		json.put("name", "Messi");
		json.put("job", "Footballer");

		request.body(json.toJSONString());

		Response response=request.post("https://reqres.in/api/users");
		System.out.println("Status of POST call is:"+response.getStatusCode());
		Assert.assertEquals("201",String.valueOf(response.getStatusCode()));
		System.out.println("Response Body is:"+response.getBody().asString());
	}

	//Post call by invoking json file
	@org.junit.Test
	public void testResponseCodeFromPostWithFile()
	{
		//We are using RequestSpecification interface because we will send request in below format
		RequestSpecification request=RestAssured.given();
		File file = new File("\\RestAssuredTesting\\RestAssuredAutomation\\src\\test\\resources\\utils\\Input.json");
		request.header("Content-Type","application/json")
		.body(file);
		Response response=request.post("https://reqres.in/api/users");
		System.out.println("Status of POST with file call is:"+response.getStatusCode());
		Assert.assertEquals("201",String.valueOf(response.getStatusCode()));
		System.out.println("Response Body is:"+response.getBody().asString());
	}


	@org.junit.Test
	public void testResponseCodeFromPut()
	{
		//We are using RequestSpecification interface because we will send request in below format
		RequestSpecification request=RestAssured.given();
		request.header("Content-Type","application/json");
		//JsonObject is a class which will help to add data in JSON format to Rest API
		//public class JSONObject extends HashMap implements Map
		JSONObject json=new JSONObject();
		json.put("name", "Messi");
		json.put("job", "Cricketer");

		request.body(json.toJSONString());

		Response response=request.put("https://reqres.in/api/users/155");
		System.out.println("Status of PUT call is:"+response.getStatusCode());
		Assert.assertEquals("200",String.valueOf(response.getStatusCode()));
		System.out.println("Response Body is:"+response.getBody().asString());
	}
}
