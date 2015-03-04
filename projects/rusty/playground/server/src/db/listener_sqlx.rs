use serde::Deserialize;
use sqlx::error::Error;
use sqlx::PgPool;
use sqlx::postgres::PgListener;

#[tokio::main]
async fn main() -> Result<(), Error> {
  let pool = PgPool::connect("postgres://playground:playground@localhost/playground").await
      .unwrap();

  let mut listener = PgListener::connect_with(&pool).await.unwrap();

  listener.listen("playground").await?;

  loop {
    println!("listening for playground updates...");
    while let Some(notification) = listener.try_recv().await? {
      let payload: Payload =
          serde_json::from_str::<Payload>(&notification.payload().to_owned()).unwrap();
      println!("{},{}", payload.key, payload.value);
    }
    println!("connection lost.");
  }
}

#[derive(Deserialize)]
pub struct Payload {
  pub key: String,
  pub value: String,
}
