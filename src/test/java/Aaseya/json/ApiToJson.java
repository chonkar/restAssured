package Aaseya.json;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.text.StringEscapeUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ApiToJson {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
            // API URL
            //URL url = new URL("https://api.coindesk.com/v1/bpi/currentprice.json");
            //URL url = new URL("https://randomuser.me/api/");
            //URL url = new URL("https://api.agify.io?name=bella");
			URL url = new URL("https://mocki.io/v1/de6f1977-d2d0-4baa-8a5c-b7e196cffd9b");
            
            // Open a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Set request method
            connection.setRequestMethod("GET");
            
            // Get response code
            int responseCode = connection.getResponseCode();
            
            // Check if response code is successful
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    StringBuffer jSon=response.append(inputLine);
                    String jSonActual=jSon.toString();
                    String unEscapeJavaString=StringEscapeUtils.unescapeJava(jSonActual.toString());
                    String string1=unEscapeJavaString.replace("\"{","{");
            		String FinalString=string1.replace("}\"","}");
                    String filePath = "D:\\RobotFramework\\json\\output.json";
                    ObjectMapper mapper = new ObjectMapper();
                	Object json = mapper.readValue(FinalString, Object.class);
                	String FinalString1=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json); 

                    // Write JSON string to file
                    try (FileWriter writer = new FileWriter(filePath)) {
                    	
                        writer.write(FinalString1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                in.close();
                
                // Print the raw JSON response
                System.out.println(response.toString());
            } else {
                // Print error message if response code is not successful
                System.out.println("Error: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	

}
