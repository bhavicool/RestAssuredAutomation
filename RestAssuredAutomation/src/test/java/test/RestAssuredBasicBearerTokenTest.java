package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredBasicBearerTokenTest {
	
	@Test
	public void getBookDetailsUsingBasicAuthentication() throws IOException {
		
		RestAssured.baseURI = getURLDetails("toolsQAURI");
		
		RequestSpecification request = RestAssured.given();
		
		
		String credentials = "TOOLSQA-Test:Test@@123";
		
		byte[] encodedCredentials = Base64.encodeBase64(credentials.getBytes());
		
		String encodedCredentialsAsString = new String(encodedCredentials);
		
		//Passing encoded Token generated above to getBooks API
		request.header("Authorization","Basic "+encodedCredentialsAsString);
		
		Response getBooksResponse = request.get("/BookStore/v1/Books");
		
		System.out.println("Response Status Code is "+getBooksResponse.getStatusCode());
		
		getBooksResponse.prettyPrint();
	}

	@Test
	public void getBookDetailsUsingBearerToken() throws IOException
	{
		RestAssured.baseURI = getURLDetails("toolsQAURI");

		RequestSpecification request = RestAssured.given();

		String payload = "{\r\n" + 
				"  \"userName\": \"TOOLSQA-Test\",\r\n" + 
				"  \"password\": \"Test@@123\"\r\n" + 
				"}";

		request.header("Content-Type","application/json");

		Response responseFromGenerateToken = request.body(payload).post("/Account/v1/GenerateToken");

		responseFromGenerateToken.prettyPrint();

		String jsonString = responseFromGenerateToken.getBody().asString();

		String tokenGenerated = JsonPath.from(jsonString).get("token");
		
		System.out.println("Token is:"+tokenGenerated);
		
		//Passing Bearer Token generated above to getBooks API
		request.header("Authorization","Bearer "+tokenGenerated);

		Response getBooksResponse = request.get("/BookStore/v1/Books");

		Assert.assertEquals(200, getBooksResponse.getStatusCode());

		getBooksResponse.prettyPrint();
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
