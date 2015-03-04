#![warn(rust_2018_idioms)]

use std::collections::HashMap;
use std::sync::mpsc::Sender;

use single_value_channel::Receiver;
use uuid::Uuid;

use crate::player::PlayerState;

const U: f32 = -0.7117853;
const W: f32 = -0.70239705;

pub fn run_sim(
  mut server_state_receiver: Receiver<HashMap<Uuid, PlayerState>>,
  player_state_updater: Sender<PlayerState>,
) {
  let mut player_state = PlayerState { t: 0., u: U, v: 0., w: W, x: 0., y: 0., z: 0. };
  let mut clock = fps_clock::FpsClock::new(30);
  loop {
    for z in 0..10 { send_state(&player_state_updater, player_state, z as f32 / 4.); }
    for z in (0..10).rev() { send_state(&player_state_updater, player_state, z as f32 / 4.); }
    /*let remote_players = */server_state_receiver.latest();
    clock.tick();
  }
}

fn send_state(tx: &Sender<PlayerState>, mut player_state: PlayerState, z: f32) {
  player_state = PlayerState { t: 0., u: U, v: 0., w: W, x: 0., y: 0., z };

  tx.send(player_state).unwrap();
}
