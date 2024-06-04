package Aaseya.json;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class readDataFromJsonFile {

	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
		
		JSONParser JsonParser= new JSONParser();
		
		FileReader reader= new FileReader("D:\\RobotFramework\\json\\output.json");
		Object obj=JsonParser.parse(reader);
		JSONObject data=(JSONObject)obj;
		
		Long count=(Long) data.get("count");
		String name=(String) data.get("name");
		Long age=(Long) data.get("age");

		System.out.println(count + name  +age);
	}

}
