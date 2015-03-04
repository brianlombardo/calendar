#![allow(clippy::wildcard_imports)]

use seed::{prelude::*, *};

use rusty_playground_client::get_playgrounds;

fn init(_: Url, _: &mut impl Orders<Msg>) -> Model {
    Model {
        data: Some("{}".to_string()),
    }
}

#[derive(Default)]
struct Model {
    data: Option<String>,
}

enum Msg {
    Request,
    Response(String),
}

fn update(msg: Msg, model: &mut Model, orders: &mut impl Orders<Msg>) {
    match msg {
        Msg::Request => {
            orders.perform_cmd(async {
                let response = get_playgrounds().await.unwrap().1;
                Some(Msg::Response(response))
            });
        }
        Msg::Response(response) => {
            model.data = Some(response);
        }
    }
}

// fn equipment_strings(json: &String) -> Vec<String> {
//     from_str::<Vec<PlaySpace>>(json.as_str())
//         .unwrap()
//         .iter()
//         .map(|play_space| &play_space.equipment)
//         .map(|equipment_vec| {
//             &equipment_vec
//                 .iter()
//                 .map(|equipment| &equipment.name_passthrough())
//         })
//         .collect::<Vec<String>>()
// }

fn view(model: &Model) -> Node<Msg> {
    div![
        button!["fetch", ev(Ev::Click, |_| Msg::Request)],
        model.data.clone().unwrap(),
        // equipment_strings(&model.data.clone().unwrap()),
    ]
}

#[wasm_bindgen(start)]
pub fn start() {
    App::start("app", init, update, view);
}
