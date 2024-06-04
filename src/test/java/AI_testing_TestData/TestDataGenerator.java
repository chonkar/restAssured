package AI_testing_TestData;

import com.github.javafaker.Faker;

public class TestDataGenerator {

    public static Object[][] generateUserData(int numUsers) {
        Object[][] testData = new Object[numUsers][7]; // 7 fields: Username, Password, Email, First Name, Last Name, Address, Phone Number

        Faker faker = new Faker();

        for (int i = 0; i < numUsers; i++) {
            String username = faker.name().username();
            String password = faker.internet().password();
            String email = faker.internet().emailAddress();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String address = faker.address().fullAddress();
            String phoneNumber = faker.phoneNumber().phoneNumber();

            testData[i][0] = username;
            testData[i][1] = password;
            testData[i][2] = email;
            testData[i][3] = firstName;
            testData[i][4] = lastName;
            testData[i][5] = address;
            testData[i][6] = phoneNumber;
        }

        return testData;
    }

    public static void main(String[] args) {
        int numUsers = 5; // Number of users to generate
        Object[][] data = generateUserData(numUsers);

        // Print the generated data
        for (Object[] user : data) {
            System.out.println("Username: " + user[0]);
            System.out.println("Password: " + user[1]);
            System.out.println("Email: " + user[2]);
            System.out.println("First Name: " + user[3]);
            System.out.println("Last Name: " + user[4]);
            System.out.println("Address: " + user[5]);
            System.out.println("Phone Number: " + user[6]);
            System.out.println("----");
        }
    }
}