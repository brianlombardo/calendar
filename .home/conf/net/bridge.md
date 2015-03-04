# bridge

```bash
apt install bridge-utils
```

## setup network

### interface definitions

```bash
vim  /etc/network/interfaces
```

```conf
allow-hotplug eth0
allow-hotplug eth1
allow-hotplug wlan0
auto br0
iface br0 inet dhcp
   bridge-ports eth0 eth1 wlan0
```

### netplan definitions

```bash
vim /etc/netplan/01-abc.yaml
```

```yaml
network:
    ethernets:
        eth0:
           dhcp4: false
           optional: true
        eth1:
           dhcp4: false
           optional: true
        wlan0:
           dhcp4: false
           optional: true
    bridges:
        br0:
            dhcp4: true
            interfaces:
                - eth0
                - eth1
                - wlan0
    version: 2
```

```bash
netplan generate
netplan apply
```

## offline host

only allow connections on bridged interfaces

```bash
apt install ufw
ufw default deny incoming
ufw default deny outgoing
ufw default deny forward
ufw enable
```

## custom network timeout

```bash
systemctl edit --full systemd-networkd-wait-online.service
# ...
# [Service]
# TimeoutStartSec=3
```
