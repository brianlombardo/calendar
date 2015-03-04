#![warn(rust_2018_idioms)]
extern crate fyrox;

use std::{env, sync::{Arc, Mutex}, thread, time::Instant};
use std::borrow::Borrow;
use std::collections::HashMap;
use std::error::Error;
use std::fmt::{Display, Formatter};
use std::net::SocketAddr;
use std::process::exit;
use std::sync::{mpsc, MutexGuard};
use std::sync::mpsc::Sender;

use futures::FutureExt;
use fyrox::{
    animation::Animation,
    core::{
        color::Color,
        math::{quat::Quat, vec3::Vec3},
        pool::Handle,
    },
    engine::resource_manager::ResourceManager,
    event::{DeviceEvent, ElementState, Event, VirtualKeyCode, WindowEvent},
    event_loop::{ControlFlow, EventLoop},
    gui::{message::TextMessage, node::StubNode, text::TextBuilder, widget::WidgetBuilder},
    scene::{
        base::BaseBuilder, camera::CameraBuilder, node::Node, Scene, transform::TransformBuilder,
    },
    utils::translate_event,
};
use fyrox::engine::Engine;
use fyrox::gui::message::MessageDirection;
use single_value_channel::Receiver;
use tokio::net::UdpSocket;
use uuid::Uuid;

use crate::player::PlayerState;

use self::fyrox::platform::unix::EventLoopExtUnix;

type GameEngine = fyrox::engine::Engine<(), StubNode>;
type UiNode = fyrox::gui::node::UINode<(), StubNode>;
type BuildContext<'a> = fyrox::gui::BuildContext<'a, (), StubNode>;

struct GameScene {
    scene: Scene,
    model_handle: Handle<Node>,
}

const ASSETS_DIRECTORY: &'static str = "fyrox";

struct InputController {
    rotate_left: bool,
    rotate_right: bool,
    forward: bool,
    backward: bool,
    left: bool,
    right: bool,
    up: bool,
    down: bool,
}

pub fn run_sim(
    mut server_state_receiver: Receiver<HashMap<Uuid, PlayerState>>,
    player_state_updater: Sender<PlayerState>,
) {
    let event_loop = EventLoopExtUnix::new_any_thread();

    let window_builder = fyrox::window::WindowBuilder::new()
        .with_title("rusty planet")
        .with_resizable(true);

    let mut engine = GameEngine::new(window_builder, &event_loop).unwrap();

    engine
        .resource_manager
        .lock()
        .unwrap();

    let debug_text = create_ui(&mut engine.user_interface.build_ctx());

    let GameScene {
        scene,
        model_handle,
    } = create_scene(engine.resource_manager.clone());

    let scene_handle = engine.scenes.add(scene);

    engine
        .renderer
        .set_ambient_color(Color::opaque(200, 40, 255));

    let clock = Instant::now();
    let fixed_timestep = 1.0 / 60.0;
    let mut elapsed_time = 0.0;

    let rotation = Quat::from_axis_angle(Vec3::new(0.0, 1.0, 0.0), 33.);
    let mut player_state = PlayerState {
        t: rotation.x,
        u: rotation.y,
        v: rotation.z,
        w: rotation.w,
        x: 0.0,
        y: 0.0,
        z: 0.0,
    };

    let mut input_controller = InputController {
        rotate_left: false,
        rotate_right: false,
        forward: false,
        backward: false,
        right: false,
        left: false,
        up: false,
        down: false,
    };

    let mut remote_player_models: HashMap<Uuid, Handle<Node>> = HashMap::new();

    event_loop.run(move |event, _, control_flow| {
        match event {
            Event::MainEventsCleared => {
                let mut dt = clock.elapsed().as_secs_f32() - elapsed_time;
                while dt >= fixed_timestep {
                    dt -= fixed_timestep;
                    elapsed_time += fixed_timestep;

                    player_state = update_player(&mut engine, model_handle, scene_handle, player_state, &mut input_controller);
                    player_state_updater.send(player_state).unwrap();

                    let remote_players = server_state_receiver.latest();
                    for (uuid, state) in remote_players {
                        if remote_player_models.contains_key(uuid) {
                            update_model(*remote_player_models.get(uuid).unwrap(),
                                         &mut engine.scenes[scene_handle]
                                         , *state);
                        } else {
                            let model_file = load_model_file(
                                "ship.fbx",
                                &mut engine.scenes[scene_handle],
                                &mut engine.resource_manager.lock().unwrap(),
                                Vec3::new(state.x, state.y, state.z),
                                Quat::from_axis_angle(Vec3::new(state.t, state.u, state.v), state.w),
                                Vec3::new(0.05, 0.05, 0.05),
                            );
                            remote_player_models.insert(*uuid, model_file);
                        }
                    }

                    let fps = engine.renderer.get_statistics().frames_per_second;
                    let text = format!("{} FPS", fps);
                    engine.user_interface.send_message(TextMessage::text(
                        debug_text,
                        MessageDirection::ToWidget,
                        text,
                    ));

                    engine.update(fixed_timestep);
                }

                engine.get_window().request_redraw();
            }
            Event::RedrawRequested(_) => {
                engine.render(fixed_timestep).unwrap();
            }
            Event::WindowEvent { event, .. } => {
                match event {
                    WindowEvent::CloseRequested => *control_flow = ControlFlow::Exit,
                    WindowEvent::Resized(size) => {
                        engine.renderer.set_frame_size(size.into());
                    }
                    WindowEvent::KeyboardInput { input, .. } => {
                        if let Some(key_code) = input.virtual_keycode {
                            match key_code {
                                VirtualKeyCode::Up => {
                                    input_controller.forward =
                                        input.state == ElementState::Pressed
                                }
                                VirtualKeyCode::Down => {
                                    input_controller.backward =
                                        input.state == ElementState::Pressed
                                }
                                VirtualKeyCode::Left => {
                                    input_controller.left =
                                        input.state == ElementState::Pressed
                                }
                                VirtualKeyCode::Right => {
                                    input_controller.right =
                                        input.state == ElementState::Pressed
                                }
                                VirtualKeyCode::W => {
                                    input_controller.forward =
                                        input.state == ElementState::Pressed
                                }
                                VirtualKeyCode::S => {
                                    input_controller.backward =
                                        input.state == ElementState::Pressed
                                }
                                VirtualKeyCode::A => {
                                    input_controller.left =
                                        input.state == ElementState::Pressed
                                }
                                VirtualKeyCode::D => {
                                    input_controller.right =
                                        input.state == ElementState::Pressed
                                }
                                VirtualKeyCode::Q => {
                                    input_controller.rotate_left =
                                        input.state == ElementState::Pressed
                                }
                                VirtualKeyCode::E => {
                                    input_controller.rotate_right =
                                        input.state == ElementState::Pressed
                                }

                                VirtualKeyCode::Space => {
                                    input_controller.up =
                                        input.state == ElementState::Pressed
                                }
                                VirtualKeyCode::LShift => {
                                    input_controller.down =
                                        input.state == ElementState::Pressed
                                }
                                VirtualKeyCode::Escape => {
                                    if input.state == ElementState::Pressed {
                                        exit(0);
                                    }
                                }
                                _ => (),
                            }
                        }
                    }
                    _ => (),
                }

                if let Some(os_event) = translate_event(&event) {
                    engine.user_interface.process_os_event(&os_event);
                }
            }
            _ => *control_flow = ControlFlow::Poll,
        }
    });
}

fn create_ui(ctx: &mut BuildContext) -> Handle<UiNode> {
    TextBuilder::new(WidgetBuilder::new()).build(ctx)
}

fn create_scene(resource_manager: Arc<Mutex<ResourceManager>>) -> GameScene {
    let mut scene = Scene::new();

    let mut resource_manager = resource_manager.lock().unwrap();

    let camera = CameraBuilder::new(
        BaseBuilder::new().with_local_transform(
            TransformBuilder::new()
                .with_local_position(Vec3::new(0., 6.0, -30.))
                .build(),
        ),
    )
        .build();

    scene.graph.add_node(Node::Camera(camera));

    let model_handle = load_model_file(
        "ship.fbx",
        &mut scene,
        &mut resource_manager,
        Vec3::new(0., 0., 0.),
        Quat::from_axis_angle(Vec3::new(0.0, 0.0, 0.0), 0.),
        Vec3::new(0.05, 0.05, 0.05),
    );

    GameScene {
        scene,
        model_handle,
    }
}

fn load_model_file(
    name: &str,
    mut scene: &mut Scene,
    resource_manager: &mut MutexGuard<ResourceManager>,
    position: Vec3,
    rotation: Quat,
    scale: Vec3,
) -> Handle<Node> {
    let model_resource = resource_manager
        .request_model(ASSETS_DIRECTORY.to_owned() + name)
        .unwrap();

    let model_handle = model_resource
        .lock()
        .unwrap()
        .instantiate_geometry(&mut scene);

    scene.graph[model_handle]
        .local_transform_mut()
        .set_position(position)
        .set_rotation(rotation)
        .set_scale(scale);

    model_handle
}

fn update_player(mut engine: &mut Engine<(), StubNode>,
                 model_handle: Handle<Node>,
                 scene_handle: Handle<Scene>,
                 player_state: PlayerState,
                 mut input_controller: &mut InputController,
) -> PlayerState {
    let scene = &mut engine.scenes[scene_handle];
    let mut player_state = player_state;

    if input_controller.forward {
        player_state.z += 1.;
    }
    if input_controller.backward {
        player_state.z -= 1.;
    }
    if input_controller.left {
        player_state.x += 1.;
    }
    if input_controller.right {
        player_state.x -= 1.;
    }
    if input_controller.up {
        player_state.y += 1.;
    }
    if input_controller.down {
        player_state.y -= 1.;
    }
    if input_controller.rotate_left {}
    if input_controller.rotate_right {}

    update_model(model_handle, scene, player_state);

    player_state
}

fn update_model(model_handle: Handle<Node>, scene: &mut Scene, mut player_state: PlayerState) {
    scene.graph[model_handle]
        .local_transform_mut()
        .set_rotation(Quat { w: player_state.w, x: player_state.t, y: player_state.u, z: player_state.v })
        .set_position(Vec3 { x: player_state.x, y: player_state.y, z: player_state.z });
}
