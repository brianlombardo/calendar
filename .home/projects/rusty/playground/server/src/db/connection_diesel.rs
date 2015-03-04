use diesel::{ExpressionMethods, RunQueryDsl};

use crate::db::{DbConnection, schema};
use crate::db::model::PlaygroundModel;
use crate::db::schema::playground::dsl::playground;
use crate::db::util_diesel::connect;

pub struct DieselDbConnection;

impl DbConnection for DieselDbConnection {
  fn fetch_all(&self) -> Vec<PlaygroundModel> {
    playground
        .load::<PlaygroundModel>(&mut connect())
        .unwrap()
  }

  fn insert(&self, play_space_json: String) {
    use schema::playground::dsl::*;
    diesel::insert_into(playground)
        .values(play_space.eq(play_space_json))
        .execute(&mut connect())
        .unwrap();
  }
}
