use std::fmt::{Display, Formatter};

#[derive(Copy, Clone)]
pub struct PlayerState {
  pub t: f32,
  pub u: f32,
  pub v: f32,
  pub w: f32,
  pub x: f32,
  pub y: f32,
  pub z: f32,
}

impl Display for PlayerState {
  fn fmt(&self, formatter: &mut Formatter<'_>) -> std::fmt::Result {
    write!(
      formatter,
      "{},{},{},{},{},{},{}",
      self.t,
      self.u,
      self.v,
      self.w,
      self.x,
      self.y,
      self.z
    )
  }
}
