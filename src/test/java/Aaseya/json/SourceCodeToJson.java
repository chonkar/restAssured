package Aaseya.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
//import org.openqa.selenium.devtools.v96.network.Network;


import java.io.File;
import java.util.*;
public class SourceCodeToJson {

	public static void main(String[] args) {
		try {
            // API URL
            //URL url = new URL("https://api.coindesk.com/v1/bpi/currentprice.json");
			URL url = new URL("https://randomuser.me/api/");
            
            
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
                    response.append(inputLine);
                }
                in.close();
                
                // Print API response
                String APIResponse=response.toString();
                System.out.println(APIResponse);
            } else {
                // Print error message if response code is not successful
                System.out.println("Error: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	

}
