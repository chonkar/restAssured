package AI_testing_TestData;
import com.github.javafaker.Faker;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoundaryTestDataGenerator {

    private static final int MIN_AGE = 0;
    private static final int MAX_AGE = 120;
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int PASSWORD_MAX_LENGTH = 64;

    public static List<User> generateBoundaryTestData() {
        List<User> testData = new ArrayList<>();
        Faker faker = new Faker();
        Random random = new Random();

        // Boundary conditions for age
        testData.add(new User(faker.name().username(), generatePassword(faker, PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH),
                faker.internet().emailAddress(), faker.name().firstName(), faker.name().lastName(), MIN_AGE));
        testData.add(new User(faker.name().username(), generatePassword(faker, PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH),
                faker.internet().emailAddress(), faker.name().firstName(), faker.name().lastName(), MAX_AGE));

        // Boundary conditions for password length
        testData.add(new User(faker.name().username(), generatePassword(faker, PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH),
                faker.internet().emailAddress(), faker.name().firstName(), faker.name().lastName(), random.nextInt((MAX_AGE - MIN_AGE) + 1) + MIN_AGE));
        testData.add(new User(faker.name().username(), generatePassword(faker, PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH),
                faker.internet().emailAddress(), faker.name().firstName(), faker.name().lastName(), random.nextInt((MAX_AGE - MIN_AGE) + 1) + MIN_AGE));

        // Boundary conditions for email
        testData.add(new User(faker.name().username(), generatePassword(faker, PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH),
                "a@b.c", faker.name().firstName(), faker.name().lastName(), random.nextInt((MAX_AGE - MIN_AGE) + 1) + MIN_AGE));
        testData.add(new User(faker.name().username(), generatePassword(faker, PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH),
                generateLongEmail(faker), faker.name().firstName(), faker.name().lastName(), random.nextInt((MAX_AGE - MIN_AGE) + 1) + MIN_AGE));

        return testData;
    }

    private static String generatePassword(Faker faker, int minLength, int maxLength) {
        if (minLength <= 0 || maxLength <= 0 || minLength > maxLength) {
            throw new IllegalArgumentException("Invalid password length bounds");
        }
        System.out.println("Generating password with length between " + minLength + " and " + maxLength);
        return faker.internet().password(minLength, maxLength);
    }

    private static String generateLongEmail(Faker faker) {
        String email = faker.internet().emailAddress();
        while (email.length() < 320) {
            email += faker.internet().emailAddress();
        }
        return email.substring(0, 320); // Limit to 320 characters
    }

    public static void writeDataToJson(List<User> data, String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // Pretty print

        // Write the output to a JSON file
        mapper.writeValue(new File(filePath), data);
    }

    public static void main(String[] args) {
        List<User> data = generateBoundaryTestData();

        String filePath = "boundary_test_data.json";
        try {
            writeDataToJson(data, filePath);
            System.out.println("Data successfully written to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing data to JSON file: " + e.getMessage());
        }
    }
}
