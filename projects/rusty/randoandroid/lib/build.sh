#!/usr/bin/env bash
set -e

rustup target add aarch64-linux-android
rustup target add x86_64-linux-android
cargo install cargo-ndk
if [[ "$1" ]]; then cargo $@; fi
cargo ndk -t arm64-v8a -o ../app/src/main/jniLibs build --release
cargo ndk -t x86_64 -o ../app/src/main/jniLibs build --release
