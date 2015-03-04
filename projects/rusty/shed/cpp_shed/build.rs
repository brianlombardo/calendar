extern crate cpp_build;

fn main() {
  build_cpp();
}

fn build_cpp() {
  cpp_build::build("src/lib.rs");
}