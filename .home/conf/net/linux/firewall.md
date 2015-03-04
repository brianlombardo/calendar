# firewall

```bash
apt install ufw
ufw enable
ufw default deny incoming
ufw default deny forward
ufw default allow outgoing
ufw allow in on eth1
ufw allow out on eth1
ufw allow 1234
ufw deny 4321
ufw allow 80/tcp
ufw allow 27015/udp
```

## custom rules

```bash
vim /etc/ufw/before.rules
```

```conf
#
# rules.before
#
...
# End required lines:

# Local Traffic User
-A ufw-before-output -m owner --uid-owner _username_ -s localhost -j ACCEPT
-A ufw-before-output -m owner --uid-owner _username_ -j DROP

# No Traffic User
-A ufw-before-output -m owner --uid-owner _username_ -j DROP

...

# disable ping
# ok icmp codes for INPUT
-A ufw-before-input -p icmp --icmp-type destination-unreachable -j DROP
-A ufw-before-input -p icmp --icmp-type source-quench -j DROP
-A ufw-before-input -p icmp --icmp-type time-exceeded -j DROP
-A ufw-before-input -p icmp --icmp-type parameter-problem -j DROP
-A ufw-before-input -p icmp --icmp-type echo-request -j DROP
```

## disable ipv6

```bash
vim /etc/default/ufw
```

set `IPV6=no`
