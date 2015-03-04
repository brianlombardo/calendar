```bash
vim /etc/pf.conf
```

```conf
ext_if="em0"
int_if="em1"
localnet="192.168.1.0/24"

allowed_hosts="{192.168.1.10, 192.168.1.20}"

lan_only_hosts="{192.168.1.100, 192.168.1.200}"

block in all
block out all

set skip on lo

pass in on $int_if from $localnet to $localnet keep state
pass out on $int_if from $localnet to $localnet keep state

# dhcp ports
pass in on $int_if proto udp from any port 68 to any port 67 keep state
pass out on $int_if proto udp from any port 67 to any port 68 keep state

pass out on $ext_if from $allowed_hosts to any nat-to ($ext_if)

block out on $ext_if from $lan_only_hosts to any
```
