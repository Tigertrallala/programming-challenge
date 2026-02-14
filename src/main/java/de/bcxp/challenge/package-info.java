/**
 * BettercallPaul Programming Challenge - Data Analysis Application.
 *
 * <p>
 * This application solves two data analysis challenges:</p>
 * <ol>
 * <li><b>Weather Challenge:</b> Find the day with the smallest temperature
 * spread</li>
 * <li><b>Countries Challenge:</b> Find the country with the highest population
 * density</li>
 * </ol>
 *
 * <h2>Package Structure</h2>
 * <ul>
 * <li>{@link de.bcxp.challenge.app} - Application entry point and CLI
 * orchestration</li>
 * <li>{@link de.bcxp.challenge.io} - Pluggable readers for CSV/JSON data
 * formats, including {@link de.bcxp.challenge.io.TabularReaderFactory} for
 * creating readers</li>
 * <li>{@link de.bcxp.challenge.logic} - Pure business logic for data
 * computations</li>
 * <li>{@link de.bcxp.challenge.util} - Reusable parsing and display utilities,
 * including {@link de.bcxp.challenge.util.ConsoleLogger} for formatted
 * output</li>
 * </ul>
 *
 * <h2>Design Principles</h2>
 * <ul>
 * <li><b>Separation of Concerns:</b> IO, logic, and utilities are cleanly
 * separated</li>
 * <li><b>Extensibility:</b> TabularReader interface allows adding new
 * formats</li>
 * <li><b>Robustness:</b> Invalid data rows are skipped with logging, not
 * exceptions</li>
 * </ul>
 *
 * <h2>Resources</h2>
 * <p>
 * Data files are located in {@code src/main/resources/de/bcxp/challenge/}:</p>
 * <ul>
 * <li>{@code weather.csv} - Temperature data with Day, MxT, MnT columns</li>
 * <li>{@code countries.csv} - Population data with Name, Population, Area
 * columns</li>
 * <li>{@code *.json} - Alternative JSON format versions of the data</li>
 * </ul>
 *
 * @see de.bcxp.challenge.app.App
 * @see de.bcxp.challenge.io.TabularReaderFactory
 * @see de.bcxp.challenge.util.ConsoleLogger
 */
package de.bcxp.challenge;
