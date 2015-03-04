+ install msys2
  + https://www.msys2.org/
  + install to `C:\root`
  + add `C:\root\usr\bin` to PATH
  + run `bash -l`

+ packages
  + find `pacman -Ss package_search`
  + install `pacman -S git vim zip unzip`
  + update `pacman -Syu`

+ windows home directory
  + `vim /etc/nsswitch.conf`
  + `db_home: windows`
  + restart bash

+ sdkman
  + `curl -s "https://get.sdkman.io" | bash`
  + find `sdk list java`
  + install `sdk install java 17.0.10-amzn`

+ idea
  + https://www.jetbrains.com/idea/download/
  + add `/c/path/to/idea/bin/` to PATH in `$HOME/.bashrc`
  + restart shell and run `idea64`

+ enable paths longer than 256 characters
  + `reg add "HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\FileSystem" -v LongPathsEnabled -t reg_dword -d 1 -f`

+ timezone fix
  + `reg add "HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\TimeZoneInformation" -v RealTimeIsUniversal -t reg_dword -d 1 -f`

+ mount EFI / ESP partition

```shell
$ diskpart
DISKPART> select disk 0
DISKPART> select partition 2
DISKPART> assign
$ cd /d/
```

+ utils

```shell
bcdedit /set hypervisorlaunchtype auto # change hyper v launch type 'auto' or 'off'
devmgmt # device manager
diskmgmt # disk paritioning tool
gpedit # group policy
msconfig # boot, services, etc
msinfo32 # system info
ncpa.cpl # network adapters
netplwiz # user manager
powercfg /h off # hibernate disable
regedit # registry editor
secpol # security policy
services.msc # services config
wf # windows firewall
```
