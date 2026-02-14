# Save test report to reports folder for git tracking
# Usage: .\save-report.ps1

$ErrorActionPreference = "Stop"

$sourceDir = "target\site"
$destDir = "reports"

if (-not (Test-Path "$sourceDir\surefire-report.html")) {
    Write-Error "No report found. Run '.\mvnw.cmd test site' first."
    exit 1
}

# Create reports folder if needed
New-Item -ItemType Directory -Force -Path $destDir | Out-Null

# Read the original HTML
$html = Get-Content "$sourceDir\surefire-report.html" -Raw -Encoding UTF8

# Read CSS files and inline them
$fluidoCss = ""
$siteCss = ""
$printCss = ""

if (Test-Path "$sourceDir\css\apache-maven-fluido-2.0.0-M6.min.css") {
    $fluidoCss = Get-Content "$sourceDir\css\apache-maven-fluido-2.0.0-M6.min.css" -Raw -Encoding UTF8
}
if (Test-Path "$sourceDir\css\site.css") {
    $siteCss = Get-Content "$sourceDir\css\site.css" -Raw -Encoding UTF8
}
if (Test-Path "$sourceDir\css\print.css") {
    $printCss = Get-Content "$sourceDir\css\print.css" -Raw -Encoding UTF8
}

# Create inline style block
$inlineStyles = @"
<style>
$fluidoCss
$siteCss
@media print { $printCss }
</style>
"@

# Replace external CSS links with inline styles
$html = $html -replace '<link rel="stylesheet"\s+href="\./css/apache-maven-fluido[^"]*"\s*/>', ''
$html = $html -replace '<link rel="stylesheet" href="\./css/site\.css"\s*/>', ''
$html = $html -replace '<link rel="stylesheet" href="\./css/print\.css"[^/]*/>', ''
$html = $html -replace '</head>', "$inlineStyles`n</head>"

# Replace icon images with HTML entity symbols (using entities to avoid encoding issues)
# Success: green checkmark
$html = $html -replace '<figure><img src="images/icon_success_sml\.gif"\s*/></figure>', '<span style="color: #16a34a; font-size: 1.4em; font-weight: bold;">&#10004;</span>'
# Error: red X
$html = $html -replace '<figure><img src="images/icon_error_sml\.gif"\s*/></figure>', '<span style="color: #dc2626; font-size: 1.4em; font-weight: bold;">&#10008;</span>'
# Warning: orange triangle
$html = $html -replace '<figure><img src="images/icon_warning_sml\.gif"\s*/></figure>', '<span style="color: #d97706; font-size: 1.4em; font-weight: bold;">&#9888;</span>'
# Info: blue circle i
$html = $html -replace '<figure><img src="images/icon_info_sml\.gif"\s*/></figure>', '<span style="color: #2563eb; font-size: 1.4em; font-weight: bold;">&#8505;</span>'
# Help: question mark
$html = $html -replace '<figure><img src="images/icon_help_sml\.gif"\s*/></figure>', '<span style="color: #6b7280; font-size: 1.4em; font-weight: bold;">&#10067;</span>'

# Save timestamped report
$timestamp = Get-Date -Format "yyyy-MM-dd_HH-mm-ss"
$timestampedFile = "$destDir\test-report_$timestamp.html"
$html | Set-Content $timestampedFile -Encoding UTF8

# Update latest report
Copy-Item $timestampedFile "$destDir\test-report-latest.html" -Force

Write-Host "Report saved (self-contained with inline CSS):"
Write-Host "  - $timestampedFile"
Write-Host "  - $destDir\test-report-latest.html"