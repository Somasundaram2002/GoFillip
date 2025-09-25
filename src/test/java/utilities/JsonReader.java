package utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class JsonReader {

    public static Object[][] getJSONData(String jsonFilePath, String jsonArrayName) {
        Object[][] data = null;
        try {
            // Read JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(jsonFilePath));
            JsonNode testDataArray = rootNode.get(jsonArrayName);

            if (testDataArray == null || !testDataArray.isArray()) {
                throw new RuntimeException("Invalid JSON structure or array not found: " + jsonArrayName);
            }

            // Get array size
            int dataSize = testDataArray.size();

            if (dataSize == 0) {
                return new Object[0][0];
            }

            // Get field names from first object to determine column count
            JsonNode firstObject = testDataArray.get(0);
            List<String> fieldNames = new ArrayList<>();
            Iterator<String> fieldIterator = firstObject.fieldNames();
            while (fieldIterator.hasNext()) {
                fieldNames.add(fieldIterator.next());
            }

            int columnCount = fieldNames.size();
            data = new Object[dataSize][columnCount];

            // Extract data dynamically
            for (int i = 0; i < dataSize; i++) {
                JsonNode testData = testDataArray.get(i);
                for (int j = 0; j < fieldNames.size(); j++) {
                    String fieldName = fieldNames.get(j);
                    JsonNode fieldValue = testData.get(fieldName);
                    data[i][j] = (fieldValue != null) ? fieldValue.asText() : "";
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
            throw new RuntimeException("Failed to read JSON file: " + jsonFilePath, e);
        }
        return data;
    }
}
