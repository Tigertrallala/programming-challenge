package de.bcxp.challenge.logic;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import de.bcxp.challenge.util.NumberParsing;

/**
 * Country data analysis: finds the country with highest population density.
 * Skips rows with missing/invalid data.
 *
 * @see NumberParsing
 */
public final class CountryLogic {

    private static final Logger LOGGER = Logger.getLogger(CountryLogic.class.getName());

    private CountryLogic() {
    }

    /**
     * Finds the country with highest population density (Population / Area).
     *
     * @param rows country data with keys: Name, Population, Area (km²)
     * @return country name, or "n/a" if no valid rows
     */
    public static String findCountryWithHighestDensity(List<Map<String, String>> rows) {
        String bestName = "n/a";
        double bestDensity = -1.0;
        int skipped = 0;

        int index = 0;
        for (Map<String, String> row : rows) {
            index++;

            // Get required values
            String name = requireValue(row, "Name", index);
            String popValue = requireValue(row, "Population", index);
            String areaValue = requireValue(row, "Area (km²)", index);
            if (name == null || popValue == null || areaValue == null) {
                skipped++;
                continue;
            }

            // Parse numbers
            double population, area;
            try {
                population = NumberParsing.parseNumber(popValue);
                area = NumberParsing.parseNumber(areaValue);
            } catch (NumberFormatException ex) {
                LOGGER.warning("Row " + index + " invalid number: " + ex.getMessage());
                skipped++;
                continue;
            }

            // Skip invalid area
            if (area <= 0.0) {
                LOGGER.warning("Row " + index + " non-positive area: " + area);
                skipped++;
                continue;
            }

            // Track highest density
            double density = population / area;
            if (density > bestDensity) {
                bestDensity = density;
                bestName = name;
            }
        }

        LOGGER.info("Countries: processed " + rows.size() + ", skipped " + skipped);
        return bestName;
    }

    // --- Helper methods ---
    private static String requireValue(Map<String, String> row, String key, int rowIndex) {
        String value = row.get(key);
        if (isMissing(value)) {
            LOGGER.warning("Row " + rowIndex + " missing: " + key);
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
