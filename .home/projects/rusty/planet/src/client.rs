#![warn(rust_2018_idioms)]

use std::{env, thread};
use std::borrow::Borrow;
use std::collections::HashMap;
use std::error::Error;
use std::fmt::{Display, Formatter};
use std::net::SocketAddr;
use std::sync::{Arc, mpsc, Mutex};
use std::sync::mpsc::Sender;

use futures::FutureExt;
use single_value_channel::channel_starting_with;
use tokio::net::UdpSocket;
use uuid::Uuid;

use game_loop::run_sim;

pub mod player;
pub mod game_loop;

#[tokio::main]
async fn main() -> Result<(), Box<dyn Error>> {
  let (player_state_updater, player_state_receiver) =
      mpsc::channel();

  let (server_state_receiver, server_state_updater):
      (
        single_value_channel::Receiver<HashMap<Uuid, player::PlayerState>>,
        single_value_channel::Updater<HashMap<Uuid, player::PlayerState>>
      ) =
      channel_starting_with(HashMap::new());

  thread::spawn(move || {
    run_sim(server_state_receiver, player_state_updater);
  });

  let remote_addr: SocketAddr = get_remote_address().parse()?;
  let local_addr: SocketAddr = get_local_address(remote_addr.is_ipv4()).parse()?;

  let socket = UdpSocket::bind(local_addr).await?;
  socket.connect(&remote_addr).await?;

  let mut remote_players: HashMap<Uuid, player::PlayerState> = HashMap::new();

  let session_uuid = Uuid::new_v4();
  for player_state in player_state_receiver {
    let message = format!("{},{}", session_uuid, player_state);
    socket.send(&(message.into_bytes())).await?;

    let mut data = vec![0u8; 65_507];
    let len = socket.recv(&mut data).await?;

    let response = String::from_utf8_lossy(&data[..len]);
    let response_items: Vec<&str> = (response.split(",")).collect();
    let remote_uuid = response_items.first().unwrap();
    let remote_state = player::PlayerState {
      t: response_items[1].parse::<f32>().unwrap(),
      u: response_items[2].parse::<f32>().unwrap(),
      v: response_items[3].parse::<f32>().unwrap(),
      w: response_items[4].parse::<f32>().unwrap(),
      x: response_items[5].parse::<f32>().unwrap(),
      y: response_items[6].parse::<f32>().unwrap(),
      z: response_items[7].parse::<f32>().unwrap(),
    };
    &remote_players.insert(Uuid::parse_str(remote_uuid).unwrap(), remote_state);
    let _ = server_state_updater.update(remote_players.clone());
    println!("{},{}", remote_uuid, remote_state);
  }

  Ok(())
}

fn get_remote_address() -> String {
  env::args()
      .nth(1)
      .unwrap_or_else(|| "127.0.0.1:8080".into())
}

fn get_local_address(is_ipv4: bool) -> String {
  if is_ipv4 {
    "0.0.0.0:0".to_string()
  } else {
    "[::]:0".to_string()
  }
}
