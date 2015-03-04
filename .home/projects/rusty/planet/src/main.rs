#![warn(rust_2018_idioms)]

use std::env;
use std::error::Error;

use tokio::net::UdpSocket;
use server::Server;

mod server;

#[tokio::main]
async fn main() -> Result<(), Box<dyn Error>> {
  let socket = UdpSocket::bind(&env::args()
      .nth(1)
      .unwrap_or_else(|| "127.0.0.1:8080".to_string()))
      .await?;
  println!("Listening on: {}", socket.local_addr()?);
  Server { socket, buf: vec![0; 1024] }.run().await?;
  Ok(())
}
