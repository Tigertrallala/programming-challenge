package de.bcxp.challenge.util;

/**
 * Console logging utility for formatted output.
 *
 * <p>
 * Provides methods for printing section headers and results in a consistent
 * format throughout the application.</p>
 */
public final class ConsoleLogger {

    /**
     * Visual separator for console output sections.
     */
    private static final String SEPARATOR = "=".repeat(50);

    /**
     * Private constructor to prevent instantiation.
     */
    private ConsoleLogger() {
    }

    /**
     * Prints a formatted section header to the console.
     *
     * @param title the section title to display
     */
    public static void printSection(String title) {
        System.out.println(SEPARATOR);
        System.out.println("  " + title);
        System.out.println(SEPARATOR);
    }

    /**
     * Prints a result message to the console.
     *
     * @param format the format string
     * @param args arguments referenced by the format specifiers
     */
    public static void printResult(String format, Object... args) {
        System.out.printf(format, args);
    }

    /**
     * Prints a blank line to the console.
     */
    public static void printBlankLine() {
        System.out.println();
    }
}
