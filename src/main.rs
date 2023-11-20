use yew::prelude::*;

#[function_component]
fn App() -> Html {

    html! {
        <div class="container px-0 mw-100">
        <textarea id="editor">{"hallo"}</textarea>
        </div>
    }
}

fn main() {
    yew::Renderer::<App>::new().render();
}