package Aaseya.json;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import POJO.api;
import POJO.getCourse;
import POJO.webAutomation;

public class OAuth_Deserialization {
	
	@Test
	public void OAuthTest() throws JsonMappingException, JsonProcessingException {
		String[] courseTitle= {"Selenium Webdriver Java","Cypress","Protractor"};
		String response= given().formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParams("grant_type", "client_credentials")
		.formParams("scope","trust")
		.when().log().all()
		.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		
		System.out.println(response);
		
		JsonPath js=new JsonPath(response);
		String token=js.getString("access_token");
		long expirationTime = js.getLong("expires_in");  
		Instant expirationInstant = Instant.ofEpochSecond(expirationTime);
		System.out.println(expirationInstant);
		Instant currentInstant = Instant.now();
		System.out.println(currentInstant);
		
			
		
		getCourse gc=given().queryParam("access_token", token)
		.when().log().all()
		.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(getCourse.class);
		//System.out.println(response2);
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		
		gc.getCourses().getApi().get(1).getCourseTitle();
		List<api> coursesAPI=gc.getCourses().getApi();
		
		for(int i=0;i<=coursesAPI.size()-1;i++) {
			
			if(gc.getCourses().getApi().get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(gc.getCourses().getApi().get(i).getPrice());
			}
		}
		
		List<webAutomation>courseWebAutomation=gc.getCourses().getWebAutomation();
		ArrayList<String> a= new ArrayList<String>();
		for(int i=0;i<=courseWebAutomation.size()-1;i++) {
			a.add(gc.getCourses().getWebAutomation().get(i).getCourseTitle());
		}
		
		List<String> orglist=Arrays.asList(courseTitle);
		Assert.assertTrue(a.equals(orglist));
		}
	

}
