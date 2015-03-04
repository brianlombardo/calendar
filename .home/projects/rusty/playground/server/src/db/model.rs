use diesel::prelude::*;
use sqlx::FromRow;
// use diesel::{Insertable,  Queryable};
// use diesel::associations::HasTable;

#[derive(Queryable, FromRow)]
pub struct PlaygroundModel {
  pub id: i64,
  pub play_space: String,
}

// #[derive(Insertable)]
// #[diesel(table_name = playground)]
// pub struct NewPlaygroundModel<'new_playground_model> {
//   pub play_space: &'new_playground_model str,
// }
