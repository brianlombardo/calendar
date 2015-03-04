init:
	rustup toolchain install stable
	rustup target add x86_64-linux-android
	rustup target add x86_64-unknown-linux-gnu
	rustup target add aarch64-linux-android
	rustup target add armv7-linux-androideabi
	rustup target add i686-linux-android
	cargo install uniffi_bindgen --version 0.17.0

bind:
	uniffi-bindgen generate src/playground.udl --language swift -o 'swift/Sources/Playground'
	uniffi-bindgen generate src/playground.udl --language kotlin -o 'android/app/src/main/java/rusty/playground/shed'

build:
	cd android && ./gradlew cargoBuild --info
