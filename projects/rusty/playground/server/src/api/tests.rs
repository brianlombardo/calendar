#[cfg(test)]
mod tests {
  use axum::http::StatusCode;
  use axum_test_helper::TestClient;
  use crate::api::controller::router;


  #[tokio::test]
  async fn test_get_playground() {
    let client = TestClient::new(router());
    let res = client.get("/v1/playground").send().await;
    assert_eq!(res.status(), StatusCode::OK);
    assert_eq!(res.text().await, "{\"equipment\":[]}");
  }

  #[tokio::test]
  async fn test_post_playground() {
    let client = TestClient::new(router());
    let play_space_json = "{\"equipment\":[\"Swing\",{\"Slide\":\"Chute\"},{\"Slide\":\"Spiral\"},{\"MerryGoRound\":{\"radius\":6}},{\"Sandbox\":{\"width\":10,\"height\":2,\"depth\":10}}]}";
    let res = client.post("/v1/playground").body(play_space_json).send().await;
    assert_eq!(res.status(), StatusCode::CREATED);
  }
}
