@echo off
@REM Run tests and generate HTML report
@REM Usage: runTests.cmd [optional maven args]

echo Running tests...
call mvnw.cmd clean test site -q %* 2>nul

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Tests failed! Check output above.
    exit /b %ERRORLEVEL%
)

echo.
echo Generating test report...
powershell -NoProfile -ExecutionPolicy Bypass -File save-report.ps1
