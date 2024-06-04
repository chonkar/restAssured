package Aaseya.json;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

public class jiraResetAssured {
	
	public static void main(String args[]) {
		RestAssured.baseURI="http://localhost:8080/";
		SessionFilter session= new SessionFilter();
		String response=given().log().all().header("Content-Type", "application/json")
		.body("{ \"username\": \"kavitachonkar25\", \"password\": \"Raumir@0801\" }")
		.log().all().filter(session)
		.when().post("/rest/auth/1/session")
		.then().extract().response().asString();
		
		
		// Add comment in JIRA
		String commentInJira= "Adding comments using Automation Date 07/May/2024";
		String addCommentResponse=given().pathParam("id", "10003").log().all().header("Content-Type", "application/json")
		.body("{\r\n"
				+ "    \"body\": \""+commentInJira+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}")
		.log().all().filter(session)
		.when().post("/rest/api/2/issue/{id}/comment")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		JsonPath js= new JsonPath(addCommentResponse);
		String commentId= js.getString("id");
		
		
		// Add attachment in JIRA
		given().header("X-Atlassian-Token","no-check").filters(session).pathParam("id", "10003")
		.header("Content-Type","multipart/form-data")
		.multiPart("file",new File("jiraattachment.txt"))
		.when().post("/rest/api/2/issue/{id}/attachments")
		.then().log().all().assertThat().statusCode(200);
		
		//Get Issue
		String response1= given().filter(session).pathParam("id", "10003")
		.queryParam("fields", "comment")
		.when().get("/rest/api/2/issue/{id}")
		.then().log().all().extract().response().asString();
		System.out.println(response1);
		
		JsonPath js1= new JsonPath(response1);
		int arraysize=js1.getInt("fields.comment.comments.size()");
		 
		for(int i=0;i<=arraysize-1;i++) {
		String commentIDIssue= js1.get("fields.comment.comments["+i+"].id").toString();
			if(commentIDIssue.equalsIgnoreCase(commentId)) {
				String newMessage=js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(newMessage);
				Assert.assertEquals(commentInJira, newMessage);
			}
			
			
		}
		
	}
		
		
		
		
}
