package de.bcxp.challenge.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import de.bcxp.challenge.util.ProgressBar;

/**
 * Reads CSV files from classpath resources into a list of row maps.
 *
 * <p>
 * Features:</p>
 * <ul>
 * <li>Configurable delimiter (default ';')</li>
 * <li>First row treated as column headers</li>
 * <li>Skips empty lines and malformed rows</li>
 * <li>Progress bar during loading</li>
 * </ul>
 *
 * @see TabularReader
 */
public final class CsvReader implements TabularReader {

    private static final char DEFAULT_DELIMITER = ';';
    private final char delimiter;

    public CsvReader() {
        this(DEFAULT_DELIMITER);
    }

    public CsvReader(char delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public List<Map<String, String>> readResource(String resourcePath) {
        // Load from classpath
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
        if (stream == null) {
            throw new IllegalArgumentException("Resource not found: " + resourcePath);
        }

        // BufferedReader for efficient line-by-line reading with UTF-8 encoding
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            if (lines.isEmpty()) {
                throw new IllegalArgumentException("Empty file: " + resourcePath);
            }

            // Parse header row
            String pattern = Pattern.quote(String.valueOf(delimiter));
            String[] headers = lines.get(0).split(pattern, -1);
            List<Map<String, String>> rows = new ArrayList<>();
            ProgressBar progress = new ProgressBar("Reading " + resourcePath);

            // Process data rows (skip header at index 0)
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.trim().isEmpty()) {
                    System.out.println("Skipping empty line " + i);
                    continue;
                }

                String[] values = line.split(pattern, -1);
                if (values.length != headers.length) {
                    System.out.println("Skipping malformed line " + i + " (expected " + headers.length + " columns, got " + values.length + ")");
                    continue;
                }

                // Map column headers to values (LinkedHashMap preserves order)
                Map<String, String> row = new LinkedHashMap<>();
                for (int j = 0; j < headers.length; j++) {
                    row.put(headers[j].trim(), values[j].trim());
                }
                rows.add(row);
                progress.update(i, lines.size() - 1);
            }
            progress.complete();
            return rows;
        } catch (IOException ex) {
            throw new UncheckedIOException("Failed to read: " + resourcePath, ex);
        }
    }
}
