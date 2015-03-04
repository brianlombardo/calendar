use std::env;

use reqwest;
use reqwest::{Error};
use rusty_playground_client::{delete_playground, get_playgrounds, post_playground};

#[tokio::main]
async fn main() -> Result<(), Error> {
  let args: Vec<String> = env::args().collect();
  let default_method = "get".to_string();
  let method = args.get(1).unwrap_or(&default_method);

  if method == "get" {
    print_result(get_playgrounds().await?);
  } else if method == "post" {
    print_result(post_playground(get_body(args)).await?);
  } else if method == "delete" {
    print_result(delete_playground().await?);
  } else {
    panic!("what is {}", method);
  }
  Ok(())
}

fn print_result(result: (String, String)) {
  println!("{}\n{}", result.0, result.1);
}


const JSON: &'static str = r#"
       {"equipment": [
        "Swing",
        { "Slide": "Chute" },
        { "MerryGoRound": { "radius": 5 }},
        { "Sandbox": { "width": 10, "height": 1, "depth": 10 }}
      ]}
  "#;
fn get_body(args: Vec<String>) -> String {
  args.get(2).unwrap_or(&JSON.to_string()).to_string()
}