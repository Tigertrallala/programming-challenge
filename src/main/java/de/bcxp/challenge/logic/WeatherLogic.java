package de.bcxp.challenge.logic;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import de.bcxp.challenge.util.NumberParsing;

/**
 * Weather data analysis: finds the day with smallest temperature spread. Skips
 * rows with missing/invalid data.
 *
 * @see NumberParsing
 */
public final class WeatherLogic {

    private static final Logger LOGGER = Logger.getLogger(WeatherLogic.class.getName());

    private WeatherLogic() {
    }

    /**
     * Finds the day with smallest temperature spread (MxT - MnT).
     *
     * @param rows weather data with keys: Day, MxT, MnT
     * @return day number, or -1 if no valid rows
     */
    public static int findDayWithSmallestSpread(List<Map<String, String>> rows) {
        int bestDay = -1;
        int bestSpread = Integer.MAX_VALUE;
        int skipped = 0;

        int index = 0;
        for (Map<String, String> row : rows) {
            index++;

            // Get required values
            String dayValue = requireValue(row, "Day", index);
            String maxValue = requireValue(row, "MxT", index);
            String minValue = requireValue(row, "MnT", index);
            if (dayValue == null || maxValue == null || minValue == null) {
                skipped++;
                continue;
            }

            // Parse numbers
            int day, maxTemp, minTemp;
            try {
                day = NumberParsing.parseInt(dayValue);
                maxTemp = NumberParsing.parseInt(maxValue);
                minTemp = NumberParsing.parseInt(minValue);
            } catch (NumberFormatException ex) {
                final int rowNum = index;
                LOGGER.warning(() -> "Row " + rowNum + " invalid number: " + ex.getMessage());
                skipped++;
                continue;
            }

            // Track smallest spread
            int spread = maxTemp - minTemp;
            if (spread < bestSpread) {
                bestSpread = spread;
                bestDay = day;
            }
        }

        final int totalRows = rows.size();
        final int skippedRows = skipped;
        LOGGER.info(() -> "Weather: processed " + totalRows + ", skipped " + skippedRows);
        return bestDay;
    }

    // --- Helper methods ---
    private static String requireValue(Map<String, String> row, String key, int rowIndex) {
        String value = row.get(key);
        if (isMissing(value)) {
            LOGGER.warning(() -> "Row " + rowIndex + " missing: " + key);
            return null;
        }
        return value;
    }

    private static boolean isMissing(String value) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }
        String lower = value.trim().toLowerCase();
        return lower.equals("nan") || lower.equals("null") || lower.equals("none") || lower.equals("undefined");
    }
}
