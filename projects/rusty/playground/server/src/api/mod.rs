use std::net::SocketAddr;

use tracing_subscriber::fmt::init;

use crate::api::controller::router;

mod controller;
mod service;
mod tests;

const PORT: u16 = 8081;

pub async fn start_server() {
  init_api().await;
}

async fn init_api() {
  init();
  let port = PORT;
  tracing::debug!("listening on {}", port);

  axum::Server::bind(&SocketAddr::from(([127, 0, 0, 1], port)))
      .serve(
        router().into_make_service()
      ).await.unwrap();
}