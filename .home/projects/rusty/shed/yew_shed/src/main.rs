use yew::prelude::*;
#[function_component]
fn App() -> Html {
  let data = use_state(|| "");
  let onclick = async {
    let counter = data.clone();
    move |_| {
      counter.set("value");
    }
  };

  html! {
    <div>
        <button {onclick}>{ "fetch" }</button>
        <p>{ *data }</p>
    </div>
    }
}

fn main() {
  yew::Renderer::<App>::new().render();
}