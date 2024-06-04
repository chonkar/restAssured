package Aaseya.json;

import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class complexParseJson {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		JsonPath js= new JsonPath(payload.courses());
		
		//Print number of courses returned by API
		int count= js.getInt("courses.size()");
		System.out.println(count);
		
		//print purchaseAmount
		int purchaseAmt=js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmt);
		
		//print title of 1st course
		String title=js.get("courses[2].title");
		System.out.println(title);
		
		//print all the courses with their respective prices
		
		for(int i=0;i<count;i++) {
			String coursesTitle=js.get("courses["+i+"].title");
			int coursesPrice=js.getInt("courses["+i+"].price");
			System.out.println(coursesTitle);
			System.out.println(coursesPrice);
		}
		
		//print number of copies sold by RPA
		System.out.println("print number of copies sold by RPA");
		for(int j=0;j<count;j++) {
			String coursesTitle=js.get("courses["+j+"].title");
			
			if(coursesTitle.equalsIgnoreCase("RPA")) {
				int RPACopies=js.get("courses["+j+"].copies");
				System.out.println(RPACopies);
				break;
			}
		}
		
		//print verify that sum of all the courses matches with purchase amount
		System.out.println("verify that sum of all the courses matches with purchase amount");
		int total=0;
		for(int j=0;j<count;j++) {
			int coursesPrice=js.getInt("courses["+j+"].price");
			int RPACopies=js.get("courses["+j+"].copies");
			int price= coursesPrice*RPACopies;
			total=total+price;
			
		}
		
		
		
		Assert.assertEquals(purchaseAmt, total);
		
		
		
	}

}
