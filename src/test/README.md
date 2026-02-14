# Test Resources and Logic Tests

This folder contains all test resources and describes the logic and integration tests for the project.

## Compact Weather Files

- `test/resources/testdata/compact_weather.csv`
- `test/resources/testdata/compact_weather.json`

Both files contain 9 rows/objects, each representing a different test scenario for weather data. The columns/fields are:

- `Day`, `MxT`, `MnT`, `Note`

### Covered Edge Cases

1. **Valid row**: All fields present and valid.
2. **Missing MxT**: MxT is empty.
3. **Missing MnT**: MnT is empty.
4. **Missing Day**: Day is empty.
5. **Invalid MxT**: MxT contains a non-numeric value.
6. **Invalid MnT**: MnT contains a non-numeric value.
7. **All missing**: All main fields are empty.
8. **Unicode**: Note contains Unicode characters (üöäß).
9. **Special chars**: Note contains special characters (!@#$%).

All these cases are asserted in the test class:

- `de.bcxp.challenge.io.CompactCsvJsonTest`

## Logic and Integration Tests

The following logic and integration tests are located in:

- `de.bcxp.challenge.logic.LogicIntegrationTest`

### What is tested?

- **WeatherLogic** and **CountryLogic** are tested with:
  - The original resource files (`weather.csv`, `countries.csv`) for correct results
  - The compact edge-case files (CSV and JSON) for robustness
  - Minimal and empty data for fallback/corner cases

### Test Sequence

1. WeatherLogic with original resource (should find day 14)
2. CountryLogic with original resource (should find Malta)
3. WeatherLogic with compact CSV (should find day 1)
4. WeatherLogic with compact JSON (should find day 1)
5. WeatherLogic with empty input (should return -1)
6. CountryLogic with minimal data (should return the only valid country)
7. CountryLogic with empty input (should return 'n/a')

All logic and edge cases are asserted in a single, compact, and popular test class for clarity and maintainability.

---

**No other test resource files are needed.**
