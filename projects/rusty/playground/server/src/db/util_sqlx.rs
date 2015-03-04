use sqlx::{Pool, Postgres};
use sqlx::postgres::PgPoolOptions;

use crate::db::{DB_URL};

pub async fn init_db() {
  sqlx::query(r#"
    create table if not exists playground (
      id bigserial,
      play_space varchar
    );"#)
      .execute(&connect().await).await
      .expect("could not create playground table");
}

pub async fn drop_db() {
  sqlx::query("drop table if exists playground;")
      .execute(&connect().await).await
      .expect("could not drop playground table");
}

pub async fn connect() -> Pool<Postgres> {
  PgPoolOptions::new()
      .max_connections(5)
      .connect(DB_URL).await
      .expect("could not create pool")
}
