use crate::api::start_server;

mod db;
mod api;

#[tokio::main]
async fn main() {
  start_server().await;
}
