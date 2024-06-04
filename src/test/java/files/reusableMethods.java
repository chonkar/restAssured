package files;

import io.restassured.path.json.JsonPath;

public class reusableMethods {
	
	public static JsonPath rawToJson(String getPlaceResonse) {
		
		JsonPath js1= new JsonPath(getPlaceResonse);
		return js1;
		
	}

}
