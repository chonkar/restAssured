package Aaseya.json;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class vehicleFaker {
    private final Map<String, Object> yamlData;
    private final Random random;

    @SuppressWarnings("unchecked")
    public vehicleFaker() {
        Yaml yaml = new Yaml();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("faker/vehicles.yml")) {
            yamlData = yaml.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load YAML file", e);
        }
        random = new Random();
    }

    @SuppressWarnings("unchecked")
    private String getRandomValue(String key) {
        List<String> values = (List<String>) ((Map<String, Object>) yamlData.get("vehicles")).get(key);
        return values.get(random.nextInt(values.size()));
    }

    public String vehicleType() {
        return getRandomValue("types");
    }

    public String vehicleBrand() {
        return getRandomValue("brands");
    }

    public String vehicleModel() {
        return getRandomValue("models");
    }

    public static void main(String[] args) {
        Faker faker = new Faker();
        vehicleFaker vehicleFaker = new vehicleFaker();

        System.out.println("Vehicle Type: " + vehicleFaker.vehicleType());
        System.out.println("Vehicle Brand: " + vehicleFaker.vehicleBrand());
        System.out.println("Vehicle Model: " + vehicleFaker.vehicleModel());
    }
}