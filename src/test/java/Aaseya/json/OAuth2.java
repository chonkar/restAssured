package Aaseya.json;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class OAuth2 {
	WebDriver driver;
	@BeforeTest
	public void OAuthTest() throws JsonMappingException, JsonProcessingException {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String expiredToken = "expired_token";
		JsonPath js;
		/*
		 * //RestAssured.baseURI=; String response= given().formParams("client_id",
		 * "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		 * .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		 * .formParams("grant_type", "client_credentials") .formParams("scope","trust")
		 * .when().log().all()
		 * .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").
		 * asString();
		 */
		Response response2 = given().header("Authorization", "Bearer " + expiredToken).when()
				.post("oauthapi/oauth2/resourceOwner/token").then().extract().response();

		
		System.out.println(response2.getStatusCode());

		if (response2.getStatusCode() == 401) {
			System.out.println("Token expired. Trying to refresh token...");
			getToken();
		}

		/*
		 * else { System.out.println("Token is valid."); getToken(); }
		 */
	}
	

	public static void getToken() throws JsonMappingException, JsonProcessingException {
		String response = given()
				.formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParams("grant_type", "client_credentials")
				.formParams("scope", "trust").when().log().all().post("/oauthapi/oauth2/resourceOwner/token")
				.asString();
		JsonPath js = new JsonPath(response);
		String validaccesstoken = js.get("access_token");
		
		 String response4=given().queryParam("access_token", validaccesstoken)
		 .when().log().all()
		 .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").asString();
		 
		System.out.println(response4);

		Path path = Paths.get("D:\\RobotFramework\\json\\output2.json");
		ObjectMapper mapper = new ObjectMapper();
		Object json = mapper.readValue(response4, Object.class);
		String FinalString1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);

		try {

			Files.writeString(path, FinalString1, StandardCharsets.UTF_8);
		} catch (IOException ex) {

			System.out.print("Invalid Path");
		}
	}
	
	@BeforeMethod
	public void setup() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options=new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		driver=new ChromeDriver(options);
		
	}
	
	@AfterMethod
	
	public void tearDown() {
		driver.close();
		 
		
	}
	
	  @DataProvider(name="dp") 
	  public String[] readJson() throws IOException, ParseException{
	  
		  //JSONParser jsoneParser=new JSONParser();
		  FileReader reader= new FileReader("D:\\RobotFramework\\json\\output2.json");
		  JsonPath js1= new JsonPath(reader);
			int arraysize=js1.getInt("courses.webAutomation.size()");
			 String arr[]=new String[arraysize];
		  
		  
		  
		  for(int i=0;i<=arraysize-1;i++) {
			  String courseTitle= js1.get("courses.webAutomation["+i+"].courseTitle").toString();
			  String price=js1.get("courses.webAutomation["+i+"].price").toString();
			  
			  
			  arr[i]=courseTitle+","+price;
		  }
		  return arr;
	 }
	  
	  @Test(dataProvider="dp")
	  void Test1(String data) throws InterruptedException {
		 
		  String formInfo[]=data.split(",");
		  driver.get("https://www.google.com");
		  driver.findElement(By.xpath("//textarea[@name='q']")).sendKeys(formInfo[0]+" courses");
		  driver.findElement(By.xpath("(//input[@name='btnK'])[2]")).click();
		  driver.findElement(By.xpath("//textarea[@name='q']")).clear();
		  driver.findElement(By.xpath("//textarea[@name='q']")).sendKeys(formInfo[1]+" courses");
		  System.out.println(formInfo[0]);
		  System.out.println(formInfo[1]);
		  
		  	
	

}

}
