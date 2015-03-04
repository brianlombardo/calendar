use rusty_playground_model::{json_to_play_space, play_space_to_json, PlaySpace};

use crate::db::connection_diesel::DieselDbConnection;
// use crate::db::connection_sqlx::SqlxDbConnection;
use crate::db::DbConnection;

pub struct Service {
  pub connection: Box<dyn DbConnection>,
}

impl Service {
  pub fn new() -> Service {
    // Service { connection: Box::new(SqlxDbConnection {}) }
    Service { connection: Box::new(DieselDbConnection {}) }
  }

  pub fn get_play_spaces(&self) -> Vec<PlaySpace> {
    self.connection.fetch_all()
        .iter()
        .map(|model| json_to_play_space(&model.play_space))
        .collect::<Vec<PlaySpace>>()
  }

  pub fn save_play_space(&self, play_space: PlaySpace) {
    self.connection.insert(play_space_to_json(&play_space));
  }
}
