package Aaseya.json;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import POJO.googleLocationAPI;
import POJO.location;

public class Serialization {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		googleLocationAPI gp= new googleLocationAPI();
		gp.setAccuracy(50);
		gp.setName("Kavita");
		gp.setPhone_number("(+91) 984 567 3937");
		gp.setAddress("Sarwasampanna Nagar");
		gp.setWebsite("www.google.com");
		gp.setLanguage("Hindi");
		
		List<String> mylist= new ArrayList<String>();
		mylist.add("hoe park");
		mylist.add("shoe");
		gp.setTypes(mylist);
		location l=new location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		gp.setLocation(l);
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key","qaclick123")
		.setContentType(ContentType.JSON).build();
		
		ResponseSpecification resspc= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		RequestSpecification res=given().spec(req).body(gp);
		Response res1=res.when().post("/maps/api/place/add/json")
		.then().log().all().spec(resspc).extract().response();
		
		String resString= res1.asString();
		System.out.println(resString);

	}

}
