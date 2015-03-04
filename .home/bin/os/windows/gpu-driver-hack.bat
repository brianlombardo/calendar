rem DXDGI_ERROR_DEVICE_HUNG
reg add "HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\GraphicsDrivers" -v "TdrDdiDelay" -t reg_dword -d 60 -f
reg add "HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\GraphicsDrivers" -v "TdrDelay" -t reg_dword -d 60 -f
