```bash
kldload if_bridge

sysrc -f /boot/loader.conf if_bridge_load="YES"
sysrc -f /etc/sysctl.conf net.inet.ip.forwarding=1

sysrc cloned_interfaces="bridge0"
# sysrc ifconfig_bridge0="inet 192.168.123.1 netmask 255.255.255.0"
# sysrc bridge_interfaces="bridge0 em0 em1"
# sysrc gateway_enable="YES"
sysrc ifconfig_bridge0="addm em0 addm em1 up"
sysrc ifconfig_em0="up"
sysrc ifconfig_em1="up"

service netif restart
service routing restart

ifconfig bridge0
```
