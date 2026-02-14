package de.bcxp.challenge.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Reads a JSON array of objects from classpath resources. Each object becomes a
 * row (Map), with all values converted to strings. Uses Jackson for parsing.
 * Example input: [{"Name":"A","Value":1},{"Name":"B","Value":2}]
 *
 * @see TabularReader
 */
public final class JsonReader implements TabularReader {

    // Shared ObjectMapper instance (thread-safe for reading)
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // Type reference for List<Map<String, Object>> - needed for Jackson generic deserialization
    private static final TypeReference<List<Map<String, Object>>> LIST_TYPE = new TypeReference<>() {
    };

    @Override
    public List<Map<String, String>> readResource(String resourcePath) {
        // Load resource from classpath
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
        if (stream == null) {
            throw new IllegalArgumentException("Resource not found: " + resourcePath);
        }

        try (stream) {
            // Parse JSON array into list of maps (values can be any type)
            List<Map<String, Object>> rawRows = MAPPER.readValue(stream, LIST_TYPE);

            // Convert all values to strings, preserving column order with LinkedHashMap
            return rawRows.stream()
                    .map(row -> row.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey, // Keep original key
                            e -> String.valueOf(e.getValue()), // Convert value to string
                            (a, b) -> a, // Merge function (keep first)
                            LinkedHashMap::new))) // Preserve insertion order
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to parse JSON: " + resourcePath, ex);
        }
    }
}
