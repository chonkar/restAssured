package Aaseya.json;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.text.StringEscapeUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import io.github.bonigarcia.wdm.WebDriverManager;

@Listeners(ListenerTest.class)	
public class ApiToJson_Testng_MultipleJson_ExtentReport {

	WebDriver driver;
	static ExtentTest test;
	static ExtentReports report;
	static ExtentSparkReporter sparkReporter;
	
	@BeforeSuite
	public void initalSetUp() {
		// TODO Auto-generated method stub
		try {
			// API URL
			//URL url1 = new URL("https://api.coindesk.com/v1/bpi/currentprice.json");
			//URL url1 = new URL("https://randomuser.me/api/");
			 URL url1 = new URL("https://api.agify.io?name=bella");
			//URL url = new URL("https://mocki.io/v1/1ca3c86f-f2a0-48d7-aece-8e252f1f7241");
			URL url = new URL("https://mocki.io/v1/9a7cd4ed-3301-4cdc-8fae-c3938deb8260");

			// Open a connection
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();

			// Set request method
			connection.setRequestMethod("GET");
			connection1.setRequestMethod("GET");
			// Get response code
			int responseCode = connection.getResponseCode();
			int responseCode1 = connection1.getResponseCode();

			// Check if response code is successful
			if (responseCode == HttpURLConnection.HTTP_OK && responseCode1 == HttpURLConnection.HTTP_OK ) {
				// Read response
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				BufferedReader in1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
				String inputLine; String inputLine2;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					StringBuffer jSon = response.append(inputLine);
					String jSonActual = jSon.toString();
					String unEscapeJavaString = StringEscapeUtils.unescapeJava(jSonActual.toString());
					String string1 = unEscapeJavaString.replace("\"{", "{");
					String FinalString = string1.replace("}\"", "}");
					String filePath = "D:\\RobotFramework\\json\\output.json";
					ObjectMapper mapper = new ObjectMapper();
					Object json = mapper.readValue(FinalString, Object.class);
					String FinalString1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);

					// Write JSON string to file
					try (FileWriter writer = new FileWriter(filePath)) {

						writer.write(FinalString1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				StringBuffer response1 = new StringBuffer();
				while ((inputLine2 = in1.readLine()) != null) {
					StringBuffer jSon = response1.append(inputLine2);
					String jSonActual = jSon.toString();
					String unEscapeJavaString = StringEscapeUtils.unescapeJava(jSonActual.toString());
					String string1 = unEscapeJavaString.replace("\"{", "{");
					String FinalString1 = string1.replace("}\"", "}");
					String filePath = "D:\\RobotFramework\\json\\output1.json";
					ObjectMapper mapper = new ObjectMapper();
					Object json = mapper.readValue(FinalString1, Object.class);
					String FinalString2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);

					// Write JSON string to file
					try (FileWriter writer = new FileWriter(filePath)) {

						writer.write(FinalString1);
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					try (FileWriter writer1 = new FileWriter(filePath)) {

						writer1.write(FinalString2);
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				in.close();

				// Print the raw JSON response
				System.out.println(response.toString());
				System.out.println(response1.toString());
			} else {
				// Print error message if response code is not successful
				System.out.println("Error: " + responseCode);
				System.out.println("Error: " + responseCode1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	@BeforeTest
	public static void startTest() {
	sparkReporter= new ExtentSparkReporter(System.getProperty("user.dir")+"/Reports/extentReport.html");
	//ExtentHtmlReporter htmlReporter =  new 	ExtentHtmlReporter(System.getProperty("user.dir")+"/Reports/extentReport.html");
	report= new ExtentReports();
	report.attachReporter(sparkReporter);
	
	 report.setSystemInfo("OS", "Windows");
	 report.setSystemInfo("Organization name", "Aaseya");
	 report.setSystemInfo("Tester Name", "Kavita");
	 
	
	 sparkReporter.config().setOfflineMode(true);
	 sparkReporter.config().setDocumentTitle("API DataDriven Report");
	 sparkReporter.config().setReportName("Daily Status Report");
	 sparkReporter.config().setTheme(Theme.DARK);
	 sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
	 sparkReporter.config().setEncoding("UTF-8");
	//htmlReporter.config().getTestViewChartLocation();
	 
	
	}
	
	@AfterTest()
	public void getResult(ITestResult result) {
		if(result.getStatus()==ITestResult.FAILURE) {
			ExtentTest test= report.createTest(result.getTestName()).addScreenCaptureFromPath("D:\\RobotFramework\\json\\Reports");
			test.log(Status.FAIL,result.getThrowable());
		}
		else if(result.getStatus()==ITestResult.SUCCESS) {
			ExtentTest test= report.createTest(result.getTestName());
			test.log(Status.PASS, result.getTestName());
		}
		else {
			test.log(Status.SKIP, result.getTestName());
		}
		
		
		report.flush();
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
	  
		  JSONParser jsoneParser=new JSONParser();
		  FileReader reader= new FileReader("D:\\RobotFramework\\json\\output.json");
		  Object obj=jsoneParser.parse(reader);
		  //JSONObject data=(JSONObject)obj;
		  JSONArray jArray=(JSONArray)obj;//(JSONArray)data.get(" ");
		  Gson gson = new Gson();
	        String json = gson.toJson(obj); //convert 
	        System.out.println(json);
		  
		  String arr[]= new String[jArray.size()];
		  
		  for(int i=0;i<=jArray.size()-1;i++) {
			  JSONObject userdata= (JSONObject)jArray.get(i);
			  String userName=(String) userdata.get("name");
			  String userLanguage=(String) userdata.get("language");
			  String userId=(String) userdata.get("id");
			  String userBio=(String) userdata.get("bio");
			  Double userversionersion=(Double) userdata.get("version");
			  
			  arr[i]=userName+","+userLanguage+","+userId +","+userBio+","+userversionersion;
		  }
		  return arr;
	 }
	  
	  @DataProvider(name="dp1") 
	  public String[] readJson1() throws IOException, ParseException{
	  
		  JSONParser jsoneParser=new JSONParser();
		  FileReader reader= new FileReader("D:\\RobotFramework\\json\\output1.json");
		  Object obj=jsoneParser.parse(reader);
		  
		 
		  
		  
		     String arr[]= new String[3];
		     for(int i=0;i<=arr.length-1;i++) {
			  JSONObject userdata= (JSONObject)obj;
			  long count=(long) userdata.get("count");
			  
			  String name=(String) userdata.get("name");
			  
			  long age=(long) userdata.get("age");
		
			  arr[i]=count+","+name+","+age;
		     }
			  
			 return arr;
	  }
	  
	 

	

	  @Test(dataProvider="dp")
	  void Test(String data) throws InterruptedException {
		  ExtentTest test= report.createTest("Form Filling");
		  String formInfo[]=data.split(",");
		  
		  driver.get("https://www.shypple.com/demo-signup-form");
		  driver.manage().window().maximize();
		  Thread.sleep(5);
		  driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id=\"hs-form-iframe-0\"]")));
		  driver.findElement(By.xpath("//input[@id='firstname-82971920-c2fd-4af3-85c3-6233f3900a37']")).sendKeys(formInfo[0]);
		  driver.findElement(By.xpath("//input[@id='lastname-82971920-c2fd-4af3-85c3-6233f3900a37']")).sendKeys(formInfo[0]);
		  driver.findElement(By.xpath("//input[@id='company-82971920-c2fd-4af3-85c3-6233f3900a37']")).sendKeys(formInfo[2]);
		  driver.findElement(By.xpath("//input[@id='email-82971920-c2fd-4af3-85c3-6233f3900a37']")).sendKeys("kavita.chonkar@aaseya.com");
		  driver.findElement(By.xpath("//input[@id='mobilephone-82971920-c2fd-4af3-85c3-6233f3900a37']")).sendKeys("7709125256");
		  Select select=new Select(driver.findElement(By.xpath("//select[@id='annual_freight_shipments-82971920-c2fd-4af3-85c3-6233f3900a37']")));
		  select.selectByVisibleText("None - I'm a first time shipper");
		  driver.findElement(By.xpath("//div[@class='input']/ul/li[1]")).click();
		  driver.findElement(By.xpath("//input[@id='what_type_of_goods_do_you_handle_0-82971920-c2fd-4af3-85c3-6233f3900a37']")).click();
		  driver.findElement(By.xpath("//input[@id='how_did_you_hear_about_us_-82971920-c2fd-4af3-85c3-6233f3900a37']")).sendKeys(formInfo[3]);
		  driver.findElement(By.xpath("//input[@type='submit']")).click();
		  
		  test.addScreenCaptureFromPath("D:\\RobotFramework\\json\\Reports");
		  
		  
		  
	  }
	  
	  @Test(dataProvider="dp1")
	  void Test1(String data) throws InterruptedException {
		  ExtentTest test= report.createTest("Google Finding Co-ordinates");
		  String formInfo[]=data.split(",");
		  driver.get("https://www.google.com");
		  driver.findElement(By.xpath("//textarea[@name='q']")).sendKeys(formInfo[0]+" cordinates");
		  driver.findElement(By.xpath("(//input[@name='btnK'])[2]")).click();
		  driver.findElement(By.xpath("//textarea[@name='q']")).clear();
		  driver.findElement(By.xpath("//textarea[@name='q']")).sendKeys(formInfo[0]+" cordinates");
		  System.out.println(formInfo[0]);
		  System.out.println(formInfo[1]);
		  System.out.println(formInfo[2]);
		  	
		  test.addScreenCaptureFromPath("D:\\RobotFramework\\json\\Reports");

}
}