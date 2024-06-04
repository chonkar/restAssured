package Aaseya.json;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;

public class GraphQLAutomaiton {
	
	public static void main(String args[]) {
		
		//Query
		int LocId=7007;
		int charId=8144;
		int epId=5059;
		EncoderConfig encoderconfig = new EncoderConfig();
		String response= given().log().all().config(RestAssured.config().encoderConfig(encoderconfig.encodeContentTypeAs("application.json", ContentType.JSON)))
				.header("Content-Type","application/json")
		.body("{\"query\":\"query($locationId:Int!,$characterId:Int!,$episodeId:Int!)\\n{\\n  location(locationId:$locationId)\\n  {\\n    id\\n    name\\n    type\\n    dimension\\n  }\\n  \\n  character(characterId:$characterId)\\n  {\\n    id\\n    name\\n    type\\n    status\\n    species\\n  }\\n  episode(episodeId:$episodeId)\\n  {\\n    id\\n    name\\n    air_date\\n    episode\\n  }\\n  \\n}\","
				+ "\"variables\":{\"locationId\":"+LocId+",\"characterId\":"+charId+",\"episodeId\":"+epId+"}}")
		.when().post("https://rahulshettyacademy.com/gq/graphql")
		.then().extract().response().asString();
		
		System.out.println(response);
		
		//mutation
		
		String responseMutation= given().log().all().config(RestAssured.config().encoderConfig(encoderconfig.encodeContentTypeAs("application.json", ContentType.JSON)))
				.header("Content-Type","application/json")
		.body("{\"query\":\"mutation ($locationName: String!, $characterName: String!, $EpisodeName: String!) {\\n  createLocation(location: {name: $locationName, type: \\\"SouthZone\\\", dimension: \\\"20\\\"}) {\\n    id\\n  }\\n  createCharacter(character: {name: $characterName, type: \\\"Macho\\\", status: \\\"alive\\\", species: \\\"fantasy\\\", gender: \\\"female\\\", image: \\\"kuchb\\\", originId: 9382, locationId: 9382}) {\\n    id\\n  }\\n  createEpisode(episode: {name: $EpisodeName, air_date: \\\"14th Aug 2024\\\", episode: \\\"11\\\"}) {\\n    id\\n  }\\n}\\n\",\"variables\":{\"locationName\":\"London\",\"characterName\":\"Chandler\",\"EpisodeName\":\"Evening in Paris\"}}")
		.when().post("https://rahulshettyacademy.com/gq/graphql")
		.then().extract().response().asString();
		
		System.out.println(responseMutation);
	}

	
}
