use std::env;
use jni::objects::{JClass, JString};
use jni::sys::jstring;
use jni::JNIEnv;
use ssh2::Session;
use std::ffi::CString;
use std::io::Read;
use std::net::TcpStream;

pub fn execute_ssh_command(
    command: &str,
    host: &str,
    username: &str,
    password: &str,
) -> Result<String, Box<dyn std::error::Error>> {
    let tcp = TcpStream::connect(host)?;
    let mut session = Session::new()?;
    session.set_tcp_stream(tcp);
    session.handshake()?;

    session.userauth_password(username, password)?;

    if !session.authenticated() {
        return Err("Authentication failed".into());
    }

    let mut channel = session.channel_session()?;
    channel.exec(command)?;

    let mut output = String::new();
    channel.read_to_string(&mut output)?;

    channel.wait_close()?;
    let exit_status = channel.exit_status()?;
    if exit_status != 0 {
        return Err(format!("Command exited with status {}", exit_status).into());
    }

    Ok(output)
}

pub fn execute_ssh_command_result(
    host: &str,
    username: &str,
    password: &str,
    command: &str,
) -> String {
    execute_ssh_command(host, username, password, command).unwrap_or_else(|e| format!("Error: {}", e))
}

#[no_mangle]
pub extern "C" fn Java_trnqilo_telecomando_command_SshCommand_execute(
    env: JNIEnv,
    _: JClass,
    command: JString,
    host: JString,
    user: JString,
    password: JString
) -> jstring {
    let command: String = env.get_string(command).expect("default").into();
    let host: String = env.get_string(host).expect("default").into();
    let user: String = env.get_string(user).expect("default").into();
    let password: String = env.get_string(password).expect("default").into();
    let output = execute_ssh_command_result(&command, &host, &user, &password);
    let output_ptr = CString::new(output).unwrap();
    env.new_string(output_ptr.to_str().unwrap())
      .unwrap()
      .into_inner()
}

fn main() {
    let args: Vec<String> = env::args().collect();
    let command = "ls -phatl; pwd; echo 'hello world'";
    let host = args.get(1).unwrap(); //"127.0.0.1:22";
    let username = args.get(2).unwrap();
    let password = args.get(3).unwrap();

    println!(
        "Command output:\n{}",
        execute_ssh_command_result(command, host, username, password)
    );
}
