package test.framework.glue;

import io.restassured.specification.RequestSpecification;
import cucumber.api.java.en.And;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class Rest {
	
	
	//Validate Response Status Code using Rest-Assured

	@And("^Demo Site Uri Request And Response Verify$") 
	public void DemoSiteDetails()
	
	{
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com/posts/1";

		RequestSpecification httpRequest = RestAssured.given();

		Response response = httpRequest.request(Method.GET,"/posts");
		
		String responseBody = response.getBody().asString();
		
		System.out.println("Response Body is =>  " + responseBody);
		
		int statusCode = response.getStatusCode();
		
		System.out.println("Status code = "+statusCode);
		
	}

	@And("^Validate Response Header using Rest-Assured$") 
	public void GetHeaders()
	{
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com/posts/1";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/posts");
	 
		String contentType = response.header("Content-Type");
		System.out.println("Content-Type value: " + contentType);
	 
		// Reader header of a give name. In this line we will get
		// Header named Server
		String serverType =  response.header("Server");
		System.out.println("Server value: " + serverType);
	 
		// Reader header of a give name. In this line we will get
		// Header named Content-Encoding
		String acceptLanguage = response.header("Content-Encoding");
		System.out.println("Content-Encoding: " + acceptLanguage);
		
		
	}	

}