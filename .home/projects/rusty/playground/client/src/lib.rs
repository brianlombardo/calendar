use reqwest;
use reqwest::{Client, Error, Response};
use serde_json::{from_str, json, to_string_pretty, Value};

const API: &'static str = "http://localhost/playground";

pub async fn get_playgrounds() -> Result<(String, String), Error> {
  respond(reqwest::get(API).await?).await
}

pub async fn post_playground(body: String) -> Result<(String, String), Error> {
  respond(Client::new()
      .post(API)
      .header("Content-Type", "application/json")
      .body(body.to_owned())
      .send().await?).await
}

pub async fn delete_playground() -> Result<(String, String), Error> {
  respond(Client::new().delete(API).send().await?).await
}

async fn respond(response: Response) -> Result<(String, String), Error> {
  let status_code = response.status().to_string();
  let json = response.text().await?;
  if json.len() > 0 {
    let json = format_json(json);
    Ok((status_code, json))
  } else {
    Ok((status_code, "".to_string()))
  }
}

fn format_json(json: String) -> String {
  let json: Value = from_str(json.as_str()).unwrap();
  to_string_pretty(&json!(json)).unwrap()
}
