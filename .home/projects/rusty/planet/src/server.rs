#![warn(rust_2018_idioms)]

use std::{env, io};
use std::collections::HashMap;
use std::net::{IpAddr, SocketAddr};

use tokio::net::UdpSocket;

pub(crate) struct Server {
  pub(crate) socket: UdpSocket,
  pub(crate) buf: Vec<u8>,
}

impl Server {
  pub(crate) async fn run(self) -> Result<(), io::Error> {
    let Server {
      socket,
      mut buf,
    } = self;

    struct Client {
      ip: String,
      port: String,
    }

    let mut clients: HashMap<u16, IpAddr> = HashMap::new();
    loop {
      if let Some((size, peer)) = Some(socket.recv_from(&mut buf).await?) {
        &clients.insert(peer.port(), peer.ip());
        for (port, ip) in &clients {
          if *port != peer.port() {
            let client_sock = SocketAddr::new(*ip, *port);
            socket.send_to(&buf[..size], &client_sock).await?;
          }
        }
      }
    }
  }
}