use std::sync::mpsc;
use std::sync::mpsc::Receiver;
use std::thread;
use std::time::Duration;

use glium::{Display, glutin, implement_vertex, IndexBuffer, program, Surface, uniform, VertexBuffer};
use glium::index::PrimitiveType;
use glutin::{ContextBuilder, window};
use glutin::event_loop::EventLoop;
use PrimitiveType::TrianglesList;
use rand::{Rng, thread_rng};
use window::WindowBuilder;

#[tokio::main]
async fn main() {
  let (tx, rx) = mpsc::channel();
  tokio::spawn(async move {
    loop {
      thread::sleep(random_duration());
      tx.send([
        Vertex { position: random_point(), color: random_color() },
        Vertex { position: random_point(), color: random_color() },
        Vertex { position: random_point(), color: random_color() },
      ]).unwrap();
    }
  });
  draw_triangle(rx);
}

fn draw_triangle(rx: Receiver<[Vertex; 3]>) {
  let event_loop = EventLoop::new();

  let display = Display::new(WindowBuilder::new(), ContextBuilder::new(), &event_loop).unwrap();

  let index_buffer = IndexBuffer::new(&display, TrianglesList, &[0u16, 1, 2]).unwrap();

  let program = program!(&display,
        140 => { vertex: PROGRAM_VERTEX, fragment: PROGRAM_FRAGMENT},
    ).unwrap();

  let draw_triangle = move || {
    implement_vertex!(Vertex, position, color);

    let mut target = display.draw();
    target.clear_color(0.0, 0.0, 0.0, 0.0);

    let vertices_from_thread =
        rx.recv().unwrap();

    target.draw(
      &VertexBuffer::new(&display, &vertices_from_thread).unwrap(),
      &index_buffer,
      &program,
      &uniform! { matrix: UNIFORM_MATRIX },
      &Default::default(),
    ).unwrap();
    target.finish().unwrap();
  };

  event_loop.run(move |_, _, _| { draw_triangle(); });
}

fn random_color() -> [f32; 3] {
  Color { red: thread_rng().gen(), green: thread_rng().gen(), blue: thread_rng().gen() }.as_array()
}

fn random_point() -> [f32; 2] {
  [thread_rng().gen_range(-1.0..1.0), thread_rng().gen_range(-1.0..1.0), ]
}

fn random_duration() -> Duration { Duration::from_secs(thread_rng().gen_range(2..5)) }

#[derive(PartialEq, Copy, Clone)]
struct Color {
  red: f32,
  green: f32,
  blue: f32,
}

impl Color {
  fn as_array(&self) -> [f32; 3] {
    [self.red, self.green, self.blue]
  }
}

#[derive(Copy, Clone)]
struct Vertex {
  position: [f32; 2],
  color: [f32; 3],
}

pub const UNIFORM_MATRIX: [[f32; 4]; 4] = [
  [1.0, 0.0, 0.0, 0.0],
  [0.0, 1.0, 0.0, 0.0],
  [0.0, 0.0, 1.0, 0.0],
  [0.0, 0.0, 0.0, 1.0f32]
];

pub const PROGRAM_VERTEX: &'static str = "#version 140
uniform mat4 matrix; in vec2 position; in vec3 color; out vec3 vColor;
void main() { gl_Position = vec4(position, 0.0, 1.0) * matrix; vColor = color; }";

pub const PROGRAM_FRAGMENT: &'static str = "#version 140
in vec3 vColor; out vec4 f_color;
void main() { f_color = vec4(vColor, 1.0); }";
