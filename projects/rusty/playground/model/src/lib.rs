use serde::{Deserialize, Serialize};
use serde_json::{from_str, to_string};

#[derive(Clone, Serialize, Deserialize)]
pub struct PlaySpace {
  pub equipment: Vec<Equipment>,
}

#[derive(Clone, Serialize, Deserialize)]
pub enum Equipment {
  Swing,
  Slide(SlideType),
  MerryGoRound { radius: u64 },
  Sandbox(Container),
}

impl PlaySpace {
  pub fn to_string_vec(&self) -> Vec<String> {
    self.equipment.iter().map(
      |equipment| equipment.name()
    ).collect::<Vec<String>>()
  }
}

#[derive(Clone, Serialize, Deserialize)]
pub enum SlideType {
  Spiral,
  Chute,
}

impl SlideType {
  pub fn to_string(&self) -> &str {
    match self {
      SlideType::Spiral => "Spiral",
      SlideType::Chute => "Chute"
    }
  }
}

#[derive(Clone, Serialize, Deserialize)]
pub struct Container {
  pub width: u64,
  pub height: u64,
  pub depth: u64,
}

pub trait EquipmentDisplay {
  fn name(&self) -> String;
}

impl EquipmentDisplay for Equipment {
  fn name(&self) -> String {
    match self {
      Equipment::Swing => { "Swing" }
      Equipment::Slide(_) => { "Slide" }
      Equipment::MerryGoRound { .. } => { "MerryGoRound" }
      Equipment::Sandbox(_) => { "Sandbox" }
    }.to_string()
  }
}

impl Equipment {
  pub fn name_passthrough(&self) -> String{
    "".to_string()
  }
}

pub fn json_to_play_space(json: &String) -> PlaySpace {
  from_str::<PlaySpace>(json.as_str()).unwrap()
}

pub fn json_to_play_spaces(json: &String) -> Vec<PlaySpace> {
  from_str::<Vec<PlaySpace>>(json.as_str()).unwrap()
}

pub fn play_space_to_json(play_space: &PlaySpace) -> String {
  to_string(&play_space).unwrap()
}

// macro_rules! json_to_play_space {
//   ($a: expr)=>{{ serde_json::from_str::<PlaySpace>($a).unwrap() }}
// }

// macro_rules! json_to_play_spaces {
//   ($a: expr)=>{{ from_str::<Vec<PlaySpace>>($a).unwrap() }}
// }

// macro_rules! play_space_to_json {
//   ($a: expr)=>{{ to_string($a).unwrap() }}
// }
