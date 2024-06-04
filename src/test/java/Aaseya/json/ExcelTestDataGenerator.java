package Aaseya.json;
 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.javafaker.Currency;
import com.github.javafaker.Faker;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelTestDataGenerator {

    public static Object[][] generateUserData(int numUsers) {
        Object[][] testData = new Object[numUsers][10]; // 7 fields: Username, Password, Email, First Name, Last Name, Address, Phone Number

        Faker faker = new Faker();

        for (int i = 0; i < numUsers; i++) {
            String username = faker.name().username();
            String password = faker.internet().password();
            String email = faker.internet().emailAddress();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String address = faker.address().fullAddress();
            String phoneNumber = faker.phoneNumber().phoneNumber();
            String DOB=faker.date().birthday().toString();
            String CCNumber= faker.business().creditCardNumber();
            String currency=faker.currency().code();

            testData[i][0] = username;
            testData[i][1] = password;
            testData[i][2] = email;
            testData[i][3] = firstName;
            testData[i][4] = lastName;
            testData[i][5] = address;
            testData[i][6] = phoneNumber;
            testData[i][7] = DOB;
            testData[i][8] = CCNumber;
            testData[i][9] = currency;
        }

        return testData;
    }

    public static void writeDataToExcel(Object[][] data, String filePath) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("User Data");

        // Create header row
        XSSFRow headerRow = sheet.createRow(0);
        String[] headers = {"Username", "Password", "Email", "First Name", "Last Name", "Address", "Phone Number","DOB","CC Number","Currency"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Fill data
        int rowCount = 1;
        for (Object[] user : data) {
            XSSFRow row = sheet.createRow(rowCount++);
            for (int i = 0; i < user.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(user[i].toString());
            }
        }

        // Write the output to a file
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }

        // Close the workbook
        workbook.close();
    }
    

    public static List<Map<String, String>> generateUserDatajson(int numUsers) {
        List<Map<String, String>> testData = new ArrayList<>();

        Faker faker = new Faker();

        for (int i = 0; i < numUsers; i++) {
            Map<String, String> user = new HashMap<>();
            user.put("Username", faker.name().username());
            user.put("Password", faker.internet().password());
            user.put("Email", faker.internet().emailAddress());
            user.put("First Name", faker.name().firstName());
            user.put("Last Name", faker.name().lastName());
            user.put("Address", faker.address().fullAddress());
            user.put("Phone Number", faker.phoneNumber().phoneNumber());

            testData.add(user);
        }

        return testData;
    }

    public static void writeDataToJson(List<Map<String, String>> data, String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // Pretty print

        // Write the output to a JSON file
        mapper.writeValue(new File(filePath), data);
    }

    public static void main(String[] args) {
        int numUsers = 100; // Number of users to generate
        Object[][] data = generateUserData(numUsers);

        String filePath = "synthetic_user_data.xlsx";
        try {
            writeDataToExcel(data, filePath);
            System.out.println("Data successfully written to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing data to Excel file: " + e.getMessage());
        }
        
        int numUsersjson = 100;
        List<Map<String, String>> datajson = generateUserDatajson(numUsersjson);

        String jsonfilePath = "synthetic_user_data.json";
        try {
            writeDataToJson(datajson, jsonfilePath);
            System.out.println("Data successfully written to " + jsonfilePath);
        } catch (IOException e) {
            System.err.println("Error writing data to JSON file: " + e.getMessage());
        }
    }
}