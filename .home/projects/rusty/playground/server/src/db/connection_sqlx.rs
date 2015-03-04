use tokio::runtime::Runtime;

use crate::db::DbConnection;
use crate::db::model::PlaygroundModel;
use crate::db::util_sqlx::connect;

pub struct SqlxDbConnection;

impl DbConnection for SqlxDbConnection {
  fn fetch_all(&self) -> Vec<PlaygroundModel> {
    Runtime::new().unwrap().block_on(async {
      sqlx::query_as::<_, PlaygroundModel>("select * from playground")
          .fetch_all(&connect().await).await
          .unwrap()
    })
  }

  fn insert(&self, play_space_json: String) {
    Runtime::new().unwrap().block_on(async {
      sqlx::query("insert into playground (play_space) values ($1) returning id;")
          .bind(play_space_json)
          .execute(&connect().await).await
          .unwrap();
    });
  }
}
