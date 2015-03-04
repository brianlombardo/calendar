### dhcp

```bash
pkg install dnsmasq
vim /usr/local/etc/dnsmasq.conf
```

```conf
interface=em1
dhcp-range=192.168.123.100,192.168.123.200,12h
dhcp-option=3,192.168.123.1
dhcp-option=6,8.8.8.8,1.1.1.1
```

```bash
sysrc dnsmasq_enable="YES"
service dnsmasq start
```

### firewall

```bash
vim /etc/pf.conf
```

```conf
ext_if="em0"
int_if="em1"
localnet="192.168.123.0/24"

nat on $ext_if from $localnet to any -> ($ext_if)

pass in on $int_if from $localnet to any keep state
pass out on $ext_if from any to any keep state
```

```bash
pfctl -sr # verify rules
pfctl -sn # verify nat
```

### configure

```bash
sysrc -f /etc/sysctl.conf net.inet.ip.forwarding=1
sysrc ifconfig_em0="DHCP"
sysrc ifconfig_em1="inet 192.168.123.1 netmask 255.255.255.0"
sysrc gateway_enable="YES"
sysrc pf_enable="YES"
```

### run

```bash
service netif restart
service routing restart
service pf start
```

### alt dhcp

```bash
pkg install isc-dhcp44-server
vim /usr/local/etc/dhcpd.conf
```

```conf
subnet 192.168.123.0 netmask 255.255.255.0 {
  range 192.168.123.100 192.168.123.200;
  option routers 192.168.123.1;
  option domain-name-servers 8.8.8.8, 1.1.1.1;
}
```

```bash
sysrc dhcpd_enable="YES "
sysrc dhcpd_ifaces="em1"
service isc-dhcpd start
```
