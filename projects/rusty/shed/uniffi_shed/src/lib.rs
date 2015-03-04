// use rusty_playground_client::post_playground;

pub fn create_playground(playground: String) -> String {
  // post_playground(playground);
  return format!("created: {}", playground);
}

include!(concat!(env!("OUT_DIR"), "/playground.uniffi.rs"));
