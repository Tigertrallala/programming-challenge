package de.bcxp.challenge.app;

import java.util.List;
import java.util.Map;

import de.bcxp.challenge.io.TabularReader;
import de.bcxp.challenge.logic.CountryLogic;
import de.bcxp.challenge.logic.WeatherLogic;
import de.bcxp.challenge.util.ConsoleLogger;

/**
 * Main application entry point for the BettercallPaul programming challenge.
 *
 * <p>
 * This application solves two data analysis challenges:</p>
 * <ul>
 * <li><b>Weather Challenge:</b> Finds the day with the smallest temperature
 * spread</li>
 * <li><b>Countries Challenge:</b> Finds the country with the highest population
 * density</li>
 * </ul>
 *
 * <p>
 * The application demonstrates separation of concerns with:</p>
 * <ul>
 * <li>{@code io} package - pluggable readers for CSV/JSON formats</li>
 * <li>{@code logic} package - pure business logic for data analysis</li>
 * <li>{@code util} package - reusable parsing and display utilities</li>
 * </ul>
 *
 * <h2>Usage</h2>
 * <pre>
 * mvnw exec:java                                    # Uses default resource files
 * mvnw exec:java -Dexec.args="weather.csv countries.csv"  # Custom files
 * </pre>
 *
 * @see WeatherLogic
 * @see CountryLogic
 * @see TabularReader
 * @see ConsoleLogger
 */
public final class App {

    /**
     * Private constructor to prevent instantiation.
     */
    private App() {
    }

    /**
     * Main entry point for the challenge application.
     *
     * @param args optional CLI arguments: args[0] = weather resource path
     * (default: de/bcxp/challenge/weather.csv) args[1] = countries resource
     * path (default: de/bcxp/challenge/countries.csv)
     */
    public static void main(String... args) {
        String weatherResource = args.length > 0 ? args[0] : "de/bcxp/challenge/weather.csv";
        String countriesResource = args.length > 1 ? args[1] : "de/bcxp/challenge/countries.csv";

        // Challenge 1: Find day with smallest temperature spread
        ConsoleLogger.printSection("CHALLENGE 1: Weather Data");
        List<Map<String, String>> weatherRows = TabularReader.read(
                weatherResource, ',', "Day", "MxT", "MnT");
        // JSON alternative (delimiter is ignored for JSON):
        // List<Map<String, String>> weatherRows = TabularReader.read(
        //         "de/bcxp/challenge/weather.json", null, "Day", "MxT", "MnT");
        int dayWithSmallestTempSpread = WeatherLogic.findDayWithSmallestSpread(weatherRows);
        ConsoleLogger.printResult("Result: Day with smallest temperature spread: %s%n", dayWithSmallestTempSpread);

        // Challenge 2: Find country with highest population density
        ConsoleLogger.printBlankLine();
        ConsoleLogger.printSection("CHALLENGE 2: Country Population Density");
        List<Map<String, String>> countryRows = TabularReader.read(
                countriesResource, null, "Name", "Population", "Area (km²)");
        // JSON alternative:
        // List<Map<String, String>> countryRows = TabularReader.read(
        //         "de/bcxp/challenge/countries.json", null, "Name", "Population", "Area (km²)");
        String countryWithHighestPopulationDensity = CountryLogic.findCountryWithHighestDensity(countryRows);
        ConsoleLogger.printResult("Result: Country with highest population density: %s%n", countryWithHighestPopulationDensity);

        // Completion message
        ConsoleLogger.printBlankLine();
        ConsoleLogger.printSection("Done!");
    }
}
