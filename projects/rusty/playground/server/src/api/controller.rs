use axum::{
  Router, routing::{delete, get, post},
};
use axum::http::StatusCode;
use axum::Json;
use rusty_playground_model::PlaySpace;

use crate::api::service::Service;
use crate::db::util_sqlx::{drop_db, init_db};

pub fn router() -> Router {
  Router::new()
      .route("/playground", get(fetch_all))
      .route("/playground", post(save))
      .route("/playground", delete(reset))
}


pub async fn fetch_all() -> (StatusCode, Json<Vec<PlaySpace>>) {
  (StatusCode::OK, Json(service().get_play_spaces()))
}


pub async fn save(Json(payload): Json<PlaySpace>) -> StatusCode {
  service().save_play_space(payload);
  StatusCode::CREATED
}

fn service() -> Service {
  Service::new()
}

pub async fn reset() -> StatusCode {
  drop_db().await;
  init_db().await;
  StatusCode::OK
}
