package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APITest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		RestAssured.baseURI=getURLDetails("apiBaseURI");

		RequestSpecification httpRequest = RestAssured.given();

		Response response = httpRequest.request(Method.GET,getURLDetails("apiPathParameter"));

		// Now let us print the body of the message to see what response
		// we have received from the server
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);

		System.out.println("Status Code is:"+response.getStatusCode());
		System.out.println("Response Header is:"+response.getHeader("Content-Type"));

		//Reading response of the API using JsonPath class

		JsonPath jsonPathEvaluator = response.jsonPath();

		Map<Object, Object> map=new HashMap<Object, Object>();
		map=jsonPathEvaluator.getMap("data");

		Set<Object> keys=map.keySet();
		for(Object o:keys)
		{
			System.out.println("Key is:"+o+" value is:"+map.get(o));
		}

		Assert.assertEquals("200",String.valueOf(response.getStatusCode()));
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


