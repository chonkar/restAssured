package Aaseya.json;

import static io.restassured.RestAssured.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.restassured.path.json.JsonPath;
public class oAuth2_0 {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		/*
		 * ChromeOptions options = new ChromeOptions();
		 * options.addArguments("--incognito");
		 * System.setProperty("webdriver.chrome.driver",
		 * "D:\\RobotFramework\\chromedriver-win64\\chromedriver.exe"); WebDriver
		 * driver=new ChromeDriver(options); driver.get(
		 * "https://accounts.google.com/o/oauth2/v2/auth?client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&scope=https://www.googleapis.com/auth/userinfo.email&redirect_uri=https://rahulshettyacademy.com/getCourse.php&auth_url=https://accounts.google.com/o/oauth2/v2/auth&response_type=code&state=Kavita123"
		 * ); driver.findElement(By.xpath("//input[@type='email']")).sendKeys(
		 * "kavitachonkar25@gmail.com");
		 * driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
		 * Thread.sleep(5000);
		 * driver.findElement(By.xpath("//span[contains(text(),'Try another way')]")).
		 * click();
		 * driver.findElement(By.xpath("//div[contains(text(),'Enter your password')]"))
		 * .click(); driver.findElement(By.xpath("//input[@type='password']")).sendKeys(
		 * "March@2503");
		 * driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
		 * //driver.findElement(By.xpath("//span[contains(text(),'Continue')]")).click()
		 * ; String url=driver.getCurrentUrl();
		 */
		String url="https://rahulshettyacademy.com/getCourse.php?state=Kavita123&code=4%2F0AdLIrYdkGRGhgPZaC2Qck7RRiEAmaHb_OXXH2hU1wlLNiZbm1rGlIjqHNBPTEkQ0m89iUg&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=consent";
		String partialCode=url.split("code=")[1];
		String actualCode=partialCode.split("&scope")[0];
		System.out.println(actualCode);
		String accessTokenResponse=given().urlEncodingEnabled(false).
				queryParams("code", actualCode)
		.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type","authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
		JsonPath js=new JsonPath(accessTokenResponse);
		String accesstoken=js.getString("access_token");
		
		String Response=given().queryParam("access_token",accesstoken)
		.when().log().all().get("https://rahulshettyacademy.com/getCourse.php").asString();
		
		System.out.println(Response);
	}

}
