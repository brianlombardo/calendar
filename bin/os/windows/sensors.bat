@echo off
for /f "skip=1 tokens=2 delims==" %%A in ('wmic /namespace:\\root\wmi PATH MSAcpi_ThermalZoneTemperature get CurrentTemperature /value') do set /a "cel=(%%~A*10)-27315"
echo %cel:~0,-2%.%cel:~-2%
