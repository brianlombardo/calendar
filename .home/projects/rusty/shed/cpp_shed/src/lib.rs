
use std::ffi::CString;

use cpp::{cpp, cpp_class};

fn get_cpp_value(name: String) -> u32 {
  let name = CString::new(name).unwrap();
  let name = name.as_ptr();
  unsafe {
    cpp!([name as "const char *"] -> u32 as "int32_t" {
        std::cout << "Hello, " << name << std::endl;
        return 42;
    })
  }
}

cpp_class!(pub unsafe struct CppClass as "CppClass");
impl CppClass {
  pub fn call_cpp_class() -> Self {
    cpp!(unsafe[]-> CppClass as "CppClass"{
      return CppClass();
    })
  }

  fn call_cpp_to_call_rust(name: String) {
    let name = CString::new(name).unwrap();
    let name = name.as_ptr();
    cpp!(unsafe [name as "const char *"] {
    auto data = name;
    rust!(rust_function [data: String as "const char *"] {
      println!("{}", data);
    });
  });
  }
}

fn create_class() {
  cpp!({
    class ThisIsCpp : AnotherCppClass {
      public:
        bool callCppLib() {
          return false;
        }
    }
  });
}

