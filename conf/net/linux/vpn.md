# vpn

```bash
sudo vim /etc/openvpn/server.conf
```

```conf
port 1234
proto tcp
dev tun
ca ca.crt
cert server.crt
key server.key
dh dh2048.pem
server 10.8.0.0 255.255.255.0
ifconfig-pool-persist ipp.txt
push "redirect-gateway def1 bypass-dhcp"
push "dhcp-option DNS 192.168.1.1"
keepalive 10 120
tls-auth ta.key 0
cipher AES-128-CBC 
comp-lzo
max-clients 4
user ovpn
group ovpn
persist-key
persist-tun
status openvpn-status.log
verb 3
;mute 20
auth SHA256
key-direction 0
;script-security 2
client-connect /etc/openvpn/clientconnect.sh
```

```bash
sudo vim /etc/openvpn/clientconnect.sh
```


```bash
#!/bin/bash
CONNECTION=$trusted_ip
touch /home/ovpn/clients.txt
if [ -z $trusted_ip ]; then CONNECTION="UNKNOWN IP"; fi
if grep -q "$CONNECTION" /home/ovpn/clients.txt; then exit 0; fi

curl -X POST --data-urlencode "payload={\"channel\": \"#CHANNEL_NAME\", \"username\": \"webhookbot\", \"text\": \"VPN connection made: $CONNECTION\", \"icon_emoji\": \":smiley:\"}" https://hooks.slack.com/services/SLACKWEBHOOK
echo $CONNECTION > /home/ovpn/clients.txt
}
```
