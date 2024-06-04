package Aaseya.json;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.payload;
import files.reusableMethods;

public class restAssured {
	
	public static void main(String args[]) throws IOException {
		//post
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response= given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(new String(Files.readAllBytes(Paths.get("D:\\RobotFramework\\json\\addlocation.json"))))
		//.body(payload.AddPlace())
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		JsonPath js= reusableMethods.rawToJson(response);//new JsonPath(response);// for parsing Json
		String placeId= js.getString("place_id");
		
		System.out.println(placeId);
		System.out.println("Place got added");
		
		
		//update place
		String newAddress= "winter walk, SA";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//get place
		String getPlaceResonse=given().log().all().queryParam("key", "qaclick123")
				.queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		//System.out.println(getPlaceResonse);
		JsonPath js1= reusableMethods.rawToJson(getPlaceResonse); //new JsonPath(getPlaceResonse);
		String actualaddress=js1.getString("address");
		System.out.println(actualaddress);
		Assert.assertEquals(newAddress,actualaddress);
	}

}
