pause
exit

::netsh advfirewall firewall set rule group="Core Networking" new enable=yes 

::flush existing rules
netsh advfirewall firewall delete rule name="TEMP" dir=out
netsh advfirewall firewall delete rule name="TEMP" dir=in
netsh advfirewall firewall delete rule name="AUTORULE" dir=out
netsh advfirewall firewall delete rule name="AUTORULE_IN" dir=in
netsh advfirewall firewall delete rule name="AUTORULE_BLOCK" dir=out
netsh advfirewall firewall delete rule name="AUTORULE_BLOCK" dir=in

@cd /d "C:\Program Files\"
call :FIREWALL_ALLOW_OUT


echo.&pause&goto:eof


:FIREWALL_ALLOW_OUT
@ setlocal enableextensions
for /R %%a in (*.exe) do (
start /b netsh advfirewall firewall add rule name="AUTORULE" dir=out program="%%a" action=allow
start /b netsh advfirewall firewall add rule name="AUTORULE_BLOCK" dir=in program="%%a" action=block
)
goto:eof


:FIREWALL_ALLOW_IN
@ setlocal enableextensions
for /R %%a in (*.exe) do (
start /b netsh advfirewall firewall add rule name="AUTORULE_IN" dir=in program="%%a" action=allow
)
goto:eof

:FIREWALL_BLOCK
@ setlocal enableextensions
for /R %%a in (*.exe) do (
start /b netsh advfirewall firewall add rule name="AUTORULE_BLOCK" dir=out program="%%a" action=block
start /b netsh advfirewall firewall add rule name="AUTORULE_BLOCK" dir=in program="%%a" action=block
)
goto:eof

:FIREWALL_BLOCK_CURRENT_DIR
@ setlocal enableextensions 
@ cd /d "%~dp0"
for /R %%a in (*.exe) do (
netsh advfirewall firewall add rule name="AUTORULE_BLOCK" dir=out program="%%a" action=block
)
goto:eof
