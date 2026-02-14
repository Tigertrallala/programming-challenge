package de.bcxp.challenge.io;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface for reading tabular data from various resource formats (CSV, JSON).
 * Each row is a map from column header to cell value.
 *
 * <p>
 * Use static factory methods to read data:</p>
 * <pre>
 * List&lt;Map&lt;String, String&gt;&gt; rows = TabularReader.read("data.csv", ',', "Name", "Value");
 * </pre>
 *
 * @see CsvReader
 * @see JsonReader
 */
public interface TabularReader {

    /**
     * Reads tabular data from a classpath resource.
     *
     * @param resourcePath path to the resource file
     * @return list of rows as column-name-to-value maps
     */
    List<Map<String, String>> readResource(String resourcePath);

    // --- Static factory methods ---
    /**
     * Reads tabular data and validates required columns are present.
     *
     * @param resourcePath classpath resource path (.csv or .json)
     * @param delimiter CSV delimiter (null = default ';', ignored for JSON)
     * @param requiredColumns columns that must exist
     * @return list of rows
     * @throws IllegalArgumentException if required columns are missing
     */
    static List<Map<String, String>> read(String resourcePath, Character delimiter, String... requiredColumns) {
        TabularReader reader = createReader(resourcePath, delimiter);
        List<Map<String, String>> rows = reader.readResource(resourcePath);

        // Validate required columns
        if (requiredColumns.length > 0 && !rows.isEmpty()) {
            Set<String> actual = rows.get(0).keySet();
            for (String col : requiredColumns) {
                if (!actual.contains(col)) {
                    throw new IllegalArgumentException(
                            "Missing column '" + col + "' in " + resourcePath + ". Available: " + actual);
                }
            }
            System.out.println("Validated columns: " + Arrays.toString(requiredColumns));
        }
        return rows;
    }

    /**
     * Creates appropriate reader based on file extension.
     */
    static TabularReader createReader(String resourcePath, Character delimiter) {
        String lower = resourcePath.toLowerCase();
        if (lower.endsWith(".json")) {
            return new JsonReader();
        }
        if (lower.endsWith(".csv")) {
            return delimiter != null ? new CsvReader(delimiter) : new CsvReader();
        }
        throw new IllegalArgumentException("Unsupported format: " + resourcePath);
    }
}
