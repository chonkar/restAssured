package Aaseya.json;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import POJO.createOrder;
import POJO.loginResponse;
import POJO.loginrequest;
import POJO.orderDetails;

public class ECommerce {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Login call
		loginrequest loginrequest=new loginrequest();
		loginrequest.setUserEmail("kavitachonkar25@gmail.com");
		loginrequest.setUserPassword("Raumir@0801");
		
	RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").setContentType(ContentType.JSON).build();
	
	RequestSpecification reqLogin=given().log().all().spec(req).body(loginrequest);
	loginResponse loginReposnse=reqLogin.when().post("api/ecom/auth/login")
	.then().log().all().extract().response().as(loginResponse.class);
	
	System.out.println(loginReposnse.getToken());
	String token=loginReposnse.getToken();
	System.out.println(loginReposnse.getUserId());
	String userId=loginReposnse.getUserId();
	
	//Add product
		RequestSpecification addProductReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
				.addHeader("authorization",token).build();
		
	RequestSpecification reqAddProduct=given().log().all().spec(addProductReq).param("productName", "Women shirts")
		.param("productAddedBy", userId)
		.param("productCategory", "fashion")
		.param("productSubCategory", "shirts")
		.param("productPrice", "11500")
		.param("productDescription", "Addias Originals")
		.param("productFor", "women")
		.multiPart("productImage",new File("D:\\personal\\Home\\Shirt.jpg"));
	
	String addProduct=reqAddProduct.when().post("api/ecom/product/add-product")
	.then().log().all().extract().asString();
	
	JsonPath js=new JsonPath(addProduct);
	String ProductId=js.get("productId").toString();
	System.out.println(ProductId);
	
	//Create product
		createOrder createorder= new createOrder();
		orderDetails od= new orderDetails();
		od.setCountry("USA");
		od.setProductOrderedId(ProductId);
		
		List orderDetailList= new ArrayList<orderDetails>();
		orderDetailList.add(od);
		createorder.setOrders(orderDetailList);
		
		RequestSpecification creatProductReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").setContentType(ContentType.JSON)
				.addHeader("authorization",token).build();
		
		RequestSpecification createProduct=given().log().all().spec(creatProductReq)
				.body(createorder);
		
		String ResponseOrder=createProduct.when().post("api/ecom/order/create-order")
		.then().log().all().extract().asString();
		
		System.out.println(ResponseOrder);
		
	//Delete Product
		RequestSpecification DeleteProductReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
				.addHeader("authorization",token).build();
		
		RequestSpecification deleteProduct=given().log().all().spec(DeleteProductReq).pathParam("productId", ProductId);
		
		String deleteProductResponse= deleteProduct.when().delete("api/ecom/product/delete-product/{productId}")
		.then().log().all().extract().asString();
		
		System.out.println(deleteProductResponse);
				
	}
	
	
	
	
}
