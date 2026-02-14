package de.bcxp.challenge.logic;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import de.bcxp.challenge.io.CsvReader;
import de.bcxp.challenge.io.JsonReader;

class LogicIntegrationTest {

    // 1. Test with original resources (realistic, full data)
    @Test
    void testWeatherLogicWithOriginalResource() {
        CsvReader reader = new CsvReader(',');
        List<Map<String, String>> rows = reader.readResource("de/bcxp/challenge/weather.csv");
        int bestDay = WeatherLogic.findDayWithSmallestSpread(rows);
        assertEquals(14, bestDay, "WeatherLogic should find day 14 as the smallest spread");
    }

    @Test
    void testCountryLogicWithOriginalResource() {
        CsvReader reader = new CsvReader(';');
        List<Map<String, String>> rows = reader.readResource("de/bcxp/challenge/countries.csv");
        String bestCountry = CountryLogic.findCountryWithHighestDensity(rows);
        assertEquals("Malta", bestCountry, "CountryLogic should find Malta as the highest density");
    }

    // 2. Test with compact edge-case files (robustness)
    @Test
    void testWeatherLogicWithCompactCsv() {
        CsvReader reader = new CsvReader(',');
        List<Map<String, String>> rows = reader.readResource("testdata/compact_weather.csv");
        int bestDay = WeatherLogic.findDayWithSmallestSpread(rows);
        assertEquals(1, bestDay, "WeatherLogic should find day 1 as the smallest spread in compact CSV");
    }

    @Test
    void testWeatherLogicWithCompactJson() {
        JsonReader reader = new JsonReader();
        List<Map<String, String>> rows = reader.readResource("testdata/compact_weather.json");
        int bestDay = WeatherLogic.findDayWithSmallestSpread(rows);
        assertEquals(1, bestDay, "WeatherLogic should find day 1 as the smallest spread in compact JSON");
    }

    // 3. Minimal and empty data
    @Test
    void testWeatherLogicWithEmptyRows() {
        List<Map<String, String>> rows = List.of();
        int bestDay = WeatherLogic.findDayWithSmallestSpread(rows);
        assertEquals(-1, bestDay, "WeatherLogic should return -1 for empty input");
    }

    @Test
    void testCountryLogicWithMinimalData() {
        List<Map<String, String>> rows = List.of(Map.of(
                "Name", "Testland",
                "Population", "1000",
                "Area (km²)", "10"
        ));
        String bestCountry = CountryLogic.findCountryWithHighestDensity(rows);
        assertEquals("Testland", bestCountry, "CountryLogic should return the only valid country");
    }

    @Test
    void testCountryLogicWithEmptyRows() {
        List<Map<String, String>> rows = List.of();
        String bestCountry = CountryLogic.findCountryWithHighestDensity(rows);
        assertEquals("n/a", bestCountry, "CountryLogic should return 'n/a' for empty input");
    }
}
