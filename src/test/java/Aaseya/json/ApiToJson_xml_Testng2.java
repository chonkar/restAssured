package Aaseya.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.text.StringEscapeUtils;

import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

public class ApiToJson_xml_Testng2 {

	WebDriver driver;
	
	public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated method stub
		try {
			
			URL url = new URL("https://mocki.io/v1/9a7cd4ed-3301-4cdc-8fae-c3938deb8260");

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
					StringBuffer jSon = response.append(inputLine);
					String jSonActual = jSon.toString();
					String unEscapeJavaString = StringEscapeUtils.unescapeJava(jSonActual.toString());
					String string1 = unEscapeJavaString.replace("\"{", "{");
					String FinalString = string1.replace("}\"", "}");
					String filePath = "D:\\RobotFramework\\json\\output.json";
					ObjectMapper mapper = new ObjectMapper();
					Object json = mapper.readValue(FinalString, Object.class);
					XmlMapper xmlMapper = new XmlMapper();
				    xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				    xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
				    xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, true);
				    
				    String xmlString = xmlMapper.writer().withRootName("root").writeValueAsString(json);
				    String filePathxml = "D:\\RobotFramework\\json\\output.xml";
				    System.out.println(xmlString);

					// Write JSON string to file
					try (FileWriter writer = new FileWriter(filePathxml)) {

						writer.write(xmlString);
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
		
		DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
		DocumentBuilder builder= factory.newDocumentBuilder();
		Document doc=(Document) builder.parse("D:\\RobotFramework\\json\\output.xml");  
		System.out.println(doc.getElementsByTagName("name"));
		
	}
	

}
