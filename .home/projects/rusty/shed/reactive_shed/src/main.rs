use std::thread;
use std::time::Duration;
use thread::sleep;

use rayon::prelude::*;
use reqwest::blocking::Response;
use rxrust::observable::of::OfObservable;
use rxrust::prelude::*;

use rusty_playground_model::{json_to_play_spaces, PlaySpace};

fn main() {
  let subscribe_scheduler = FuturesThreadPoolScheduler::new().unwrap();
  get_playground_observable()
      .subscribe_on(subscribe_scheduler.clone())
      .map(response_to_json_string)
      .map(json_string_to_play_spaces)
      .map(play_spaces_to_equipment_count_csv)
      .observe_on_threads(subscribe_scheduler.clone())
      .subscribe(move |equipment_count_csv| println!("{}", equipment_count_csv));

  println!("subscribed:");
  sleep(Duration::from_secs(1));
}

fn get_playground_observable() -> OfObservable<Response> {
  of(get_playground_response())
}

fn get_playground_response() -> Response {
  match reqwest::blocking::get("http://localhost/playground") {
    Ok(response) => response,
    Err(err) => panic!("Error: {}", err)
  }
}

fn response_to_json_string(response: Response) -> String {
  response.text().unwrap().to_string()
}

fn json_string_to_play_spaces(string: String) -> Vec<PlaySpace> {
  json_to_play_spaces(&string)
}

fn play_spaces_to_equipment_count_csv(play_spaces: Vec<PlaySpace>) -> String {
  play_spaces.par_iter()
      .map(play_space_to_equipment_count)
      .collect::<Vec<String>>()
      .join(",")
}

fn play_space_to_equipment_count(play_space: &PlaySpace) -> String {
  play_space.equipment.len().to_string()
}
