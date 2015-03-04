use jni::objects::{JClass, JString};
use jni::sys::{jint, jstring};
use jni::JNIEnv;
use rand::Rng;
use std::ffi::CString;

#[no_mangle]
pub extern "C" fn Java_trnqilo_rustyrando_Rando_getRando(
    env: JNIEnv,
    _: JClass,
    label: JString,
    max: jint,
) -> jstring {
    let mut rng = rand::thread_rng();
    let label: String = env.get_string(label).expect("default").into();
    let max = max as u32;
    let random_num = rng.gen_range(0..max);
    let output = format!("{}: {}", label, random_num);
    let output_ptr = CString::new(output).unwrap();
    env.new_string(output_ptr.to_str().unwrap())
        .unwrap()
        .into_inner()
}
