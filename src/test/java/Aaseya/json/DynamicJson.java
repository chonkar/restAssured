package Aaseya.json;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.gherkin.model.Given;

import files.payload;
import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {
	
	@Test(dataProvider="BooksData")
	public void addBook(String isbn, String asile) {
		RestAssured.baseURI="http://216.10.245.166";
		String response= given().log().all().header("Content-Type","application/json")
		.body(payload.Addbook(isbn,asile))
		.when()
		.post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).body("Msg", equalTo("successfully added"))
		.extract().response().asString();
		
		JsonPath js= reusableMethods.rawToJson(response);
		String id= js.get("ID");
		System.out.println(id);
		
}
	
	@Test(dataProvider="BooksData")
	public void deleteBook(String isbn, String asile) {
		RestAssured.baseURI="http://216.10.245.166";
		String Id=asile+isbn;
		String response= given().log().all().header("Content-Type","application/json")
		.body(payload.Deletebook(Id))
		.when()
		.post("/Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200).body("Msg", equalTo("book is successfully deleted"))
		.extract().response().asString();
		
		JsonPath js= reusableMethods.rawToJson(response);
		String id= js.get("ID");
		System.out.println(id);
		
}

	@DataProvider(name="BooksData")
	public Object[][] getData() {
		return new Object[][] {{"sajds","832"},{"dhjahd","343"},{"ewhgf","jdsf"}};
	}
}