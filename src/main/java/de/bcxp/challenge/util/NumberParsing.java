package de.bcxp.challenge.util;

/**
 * Utility class for parsing numbers from European locale formats.
 *
 * <p>
 * Handles EU number formats:</p>
 * <ul>
 * <li>{@code "1.234.567"} - dots as thousands separators</li>
 * <li>{@code "1234,56"} - comma as decimal separator</li>
 * <li>{@code "1.234.567,89"} - combined EU format</li>
 * </ul>
 */
public final class NumberParsing {

    private NumberParsing() {
    }

    /**
     * Parses a string as an integer, handling EU format.
     *
     * @param value the string value to parse
     * @return the parsed integer
     * @throws NumberFormatException if the value cannot be parsed
     */
    public static int parseInt(String value) {
        return Integer.parseInt(normalize(value));
    }

    /**
     * Parses a string as a double, handling EU format.
     *
     * @param value the string value to parse
     * @return the parsed double
     * @throws NumberFormatException if the value cannot be parsed
     */
    public static double parseNumber(String value) {
        return Double.parseDouble(normalize(value));
    }

    /**
     * Normalizes EU format to parseable format (comma to dot, remove thousand
     * separators).
     */
    private static String normalize(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new NumberFormatException("null or empty");
        }
        return value.trim()
                .replace(".", "") // Remove thousand separators
                .replace(',', '.'); // Convert decimal separator
    }
}
