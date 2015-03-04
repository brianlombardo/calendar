use crate::db::model::PlaygroundModel;

pub mod connection_sqlx;
pub mod connection_diesel;
pub mod schema;
pub mod model;
pub mod util_sqlx;
pub mod util_diesel;

pub trait DbConnection {
  fn fetch_all(&self) -> Vec<PlaygroundModel>;
  fn insert(&self, play_space_json: String);
}

const DB_URL: &'static str = "postgres://playground:playground@localhost/playground";
