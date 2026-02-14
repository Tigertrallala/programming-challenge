package de.bcxp.challenge.io;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class CompactCsvJsonTest {

    @Test
    void testCompactWeatherCsvCases() {
        CsvReader reader = new CsvReader(',');
        List<Map<String, String>> rows = reader.readResource("testdata/compact_weather.csv");
        assertCompactWeatherCases(rows);
    }

    @Test
    void testCompactWeatherJsonCases() {
        JsonReader reader = new JsonReader();
        List<Map<String, String>> rows = reader.readResource("testdata/compact_weather.json");
        assertCompactWeatherCases(rows);
    }

    private void assertCompactWeatherCases(List<Map<String, String>> rows) {
        assertEquals(9, rows.size(), "Should read all 9 rows");
        // Row 0: Valid
        assertEquals("1", rows.get(0).get("Day"));
        assertEquals("88", rows.get(0).get("MxT"));
        assertEquals("59", rows.get(0).get("MnT"));
        // Row 1: Missing MxT
        assertEquals("", rows.get(1).get("MxT"), "Row 1: MxT should be empty");
        // Row 2: Missing MnT
        assertEquals("", rows.get(2).get("MnT"), "Row 2: MnT should be empty");
        // Row 3: Missing Day
        assertEquals("", rows.get(3).get("Day"), "Row 3: Day should be empty");
        // Row 4: Invalid MxT (notanumber)
        assertEquals("notanumber", rows.get(4).get("MxT"), "Row 4: MxT should be 'notanumber'");
        // Row 5: Invalid MnT (notanumber)
        assertEquals("notanumber", rows.get(5).get("MnT"), "Row 5: MnT should be 'notanumber'");
        // Row 6: All missing
        assertEquals("6", rows.get(6).get("Day"), "Row 6: Day should be '6'");
        assertEquals("", rows.get(6).get("MxT"), "Row 6: MxT should be empty");
        assertEquals("", rows.get(6).get("MnT"), "Row 6: MnT should be empty");
        // Row 7: Unicode
        assertTrue(rows.get(7).get("Note").contains("ü"), "Row 7: Should contain unicode");
        // Row 8: Special chars
        assertTrue(rows.get(8).get("Note").contains("!@#$%"), "Row 8: Should contain special chars");
    }
}
