package AI_testing_TestData;

import java.io.IOException;
import java.util.List;

public class SyntheticDataWorkflow {
    public static void main(String[] args) {
        try {
            // Step 1: Scrape Data
            List<String> scrapedData = WebScraper.scrapeData("https://www.google.com","//textarea[@name='q']");

            // Step 2: Clean Data
            List<String> cleanedData = DataCleaner.cleanData(scrapedData);

            // Step 3: Generate Synthetic Data
            StringBuilder prompt = new StringBuilder("Generate synthetic data based on the following examples:\n");
            for (String data : cleanedData) {
                prompt.append("- ").append(data).append("\n");
            }
            String syntheticData = ChatGPTClient.generateSyntheticData(prompt.toString());

            // Step 4: Save Synthetic Data
            DataSaver.saveToJson(List.of(syntheticData), "synthetic_data.json");

            System.out.println("Synthetic data generated and saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

