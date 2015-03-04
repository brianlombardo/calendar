# router

```bash
apt install dnsmasq ufw
```

### enable forwarding

```bash
vim /etc/sysctl.conf
```

uncomment or set `net.ipv4.ip_forward=1`

## setup network

### interface definitions

```bash
vim /etc/network/interfaces.d/router
```

#### single lan

```conf
# auto eth0 # uncomment to pause boot until connected
allow-hotplug eth0
iface eth0 inet dhcp
        # hwaddress ether aa:bb:cc:dd:ee:ff


# create a single lan network
# auto eth1
allow-hotplug eth1
iface eth1 inet static
       address 192.168.1.1
       network 192.168.1.0
       netmask 255.255.255.0
       broadcast 192.168.1.255
```

#### bridge

```conf
allow-hotplug eth1
allow-hotplug wlan0
auto br0
iface br0 inet static
       address 192.168.1.1
       network 192.168.1.0
       netmask 255.255.255.0
       broadcast 192.168.1.255
       bridge-ports eth1 wlan0
```

#### dhcpcd interfaces

```conf
iface eth1 inet manual
iface wlan0 inet manual

auto br0
iface br0 inet manual
  bridge_ports eth1 wlan0
```

```bash
vim /etc/dhcpcd.conf
```

```conf
noipv6rs
noipv6
denyinterfaces eth1 wlan0
interface br0
    static ip_address=192.168.1.1/24
    static domain_name_servers=127.0.0.1 # 8.8.8.8 1.1.1.1
```

### netplan definitions

```bash
vim /etc/netplan/01-abc.yaml
```

#### single lan

```yaml
network:
    ethernets:
        eth0:
            dhcp4: true
            macaddress: 00:01:02:03:04:05 # optional fake mac
        eth1:
            addresses:
            - 192.168.1.1/24
            dhcp4: false
            nameservers:
                addresses:
                - 1.1.1.1
                - 8.8.8.8
                - 8.8.4.4
                search: []
            optional: true
    version: 2
```

#### wireless wan

```yaml
network:
    wifis:
      wlan0:
          access-points:
              ssidwasd:
                  password: xxxxxxxxxxxxxxxx
              ssidhjkl:
                  password: zzzzzzzzzzzzzzzz
          dhcp4: true
    ethernets:
        eth1:
            addresses:
            - 192.168.1.1/24
            dhcp4: false
            nameservers:
                addresses:
                - 1.1.1.1
                - 8.8.8.8
                - 8.8.4.4
                search: []
            optional: true
    version: 2
```

#### bridge

```yaml
network:
    ethernets:
        eth0:
           dhcp4: true
        eth1:
           dhcp4: false
           optional: true
        eth2:
           dhcp4: false
           optional: true
        wlan0:
           dhcp4: false
           optional: true
    bridges:
        br0:
            addresses:
            - 192.168.123.1/24
            dhcp4: false
            nameservers:
                addresses:
                - 1.1.1.1
                - 8.8.8.8
                - 8.8.4.4
                search: []
            interfaces:
                - eth1
                - eth2
                - wlan0
    version: 2
```

#### wake on lan

```yaml
network:
  version: 2
  renderer: networkd
  ethernets:
    eth0:
      match:
        macaddress: aa:bb:cc:dd:ee:ff
      dhcp4: true
      wakeonlan: true
```

#### network manager

```yaml
network:
  version: 2
  renderer: NetworkManager
```

```bash
netplan generate
netplan apply
```

## setup server

```bash
vim /etc/dnsmasq.d/router.conf
```

```conf
no-resolv
server=1.1.1.1
addn-hosts=/etc/hosts_custom

interface=br0
dhcp-range=192.168.1.100,192.168.1.250,72h
dhcp-host=00:11:22:33:44:55,192.168.1.123,static_host_name
dhcp-host=55:44:33:22:11:00,ignore
```

### force static hosts only
```conf
dhcp-range=192.168.1.1,192.168.1.1,0m
```

### flush dnsmasq leases
```bash
sh -c 'echo > /var/lib/misc/dnsmasq.leases'
```

### view clients

```bash
arp -a
# or
ip n | nslookup
```

### error: dnsmasq port 53 in use

```bash
systemctl stop systemd-resolved
systemctl disable systemd-resolved
systemctl mask systemd-resolved
```

## setup firewall

```bash
vim /etc/ufw/before.rules
```

```conf
#
# rules.before
#
# Rules that should be run before the ufw command line added rules. Custom
# rules should be added to one of these chains:
#   ufw-before-input
#   ufw-before-output
#   ufw-before-forward
#

# Router Rules
*nat
:PREROUTING ACCEPT [0:0]
:POSTROUTING ACCEPT [0:0]
-F
# Forward Port to IP
# -A PREROUTING -i eth0 -p tcp --dport 27015 -j DNAT --to-destination 192.168.1.123
# Route Subnet to WAN
-A POSTROUTING -s 192.168.1.0/24 -o eth0 -j MASQUERADE
# Route IP to WAN
# -A POSTROUTING -s 192.168.1.123 -o eth0 -j MASQUERADE # per IP masquerade
COMMIT
# End Router Rules

# Don't delete these required lines, otherwise there will be errors
```

```bash
ufw enable
ufw default deny incoming
ufw default allow outgoing
ufw default allow forward

ufw allow in on eth1
ufw allow in on br0

ufw status numbered
```
