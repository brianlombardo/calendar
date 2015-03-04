if [[ -f "$HOME/.profo" ]]; then source "$HOME/.profo"; fi
export DOTHOME="${DOTHOME:-$HOME/.home}" HOLO="${HOLO:-$HOME/.local/home}"
export HOMELIB="${HOMELIB:-$HOME/Library}" PROJECTS="${PROJECTS:-$HOME/Projects}"
export ANDROID_HOME="${ANDROID_HOME:-$HOMELIB/android/sdk}"
export ANDROID_SDK_ROOT="$ANDROID_HOME" ANDROID_NDK_HOME="$ANDROID_HOME/ndk" ANDROID_NDK_ROOT="$ANDROID_HOME/ndk"
export GOROOT="$HOMELIB/go" SWIFT_HOME="$HOMELIB/swift/usr" SDKMAN_DIR="$HOME/.sdkman"
export awk="${awk:-gawk}"

function include { while [[ "$1" ]]; do if [[ -d "$1" ]]; then export PATH="$1:$PATH"; fi; shift; done; }
function import { while [[ "$1" ]]; do if [[ -s "$1" ]]; then source "$1"; fi; shift; done; }
function _home_env_setup {
  local OS="${OSTYPE:-`uname -s | $awk '{print tolower($1)}'`}"
  if [[ "$OS" == 'darwin'* ]]; then export whereami='darwin'
  elif [[ "$OS" == 'freebsd'* ]]; then export whereami='freebsd'
  elif [[ "$OS" == *'android'* ]]; then export whereami='android'
  elif [[ "$OS" == 'openbsd'* ]]; then export whereami='openbsd'
  elif [[ "$OS" == 'netbsd'* ]]; then export whereami='netbsd'
  elif [[ "$OS" == 'msys'* || "$OS" == 'cygwin' ]]; then export whereami='windows'
  elif [[ "$OS" == 'linux'* && "`uname -r`" =~ .*'-ish'$ ]]; then whereami='ios'
  elif [[ "$OS" == 'linux'* ]]; then export whereami='linux'
  else export whereami='unknown'; fi

  local BIN="$DOTHOME/bin"
  local BIN_OS="$BIN/os/$whereami" BIN_DEV="$BIN/dev" BIN_SH="$BIN/shell"
  export PATH="$BIN/home:$BIN/home/key:$BIN/calc:$BIN/net:$BIN/os:$BIN/sys:$PATH"
  export PATH="$BIN_SH:$BIN_SH/file:$BIN_SH/string:$BIN_SH/time:$BIN_SH/run:$PATH"
  export PATH="$BIN_DEV:$BIN/app:$BIN_OS:$PATH"

  include \
    "$HOLO/bin" "$HOME/.local/bin" \
    "$HOME/.cargo/bin" "$HOME/.pub-cache/bin" "$SWIFT_HOME/bin" "$HOMELIB/flutter/bin" "$HOMELIB/code/bin" \
    "$HOMELIB/dart-sdk/bin" "$HOMELIB/python" "$HOMELIB/kotlin-native/bin" "$GOROOT/bin" "$HOMELIB/zig" \
    "$ANDROID_HOME/tools" "$ANDROID_HOME/tools/bin" "$ANDROID_HOME/platform-tools" \
    "$ANDROID_HOME/emulator" "$ANDROID_HOME/cmdline-tools/latest/bin"

  import "$DOTHOME/source/"* "$DOTHOME/lib/profile" "$DOTHOME/play/profile" "$HOLO/profile" "$HOME/.local/profile" \
    "$HOME/.cargo/env" "$SDKMAN_DIR/bin/sdkman-init.sh"

  if [[ -d "$HOME/.local/profiles/" ]]; then import "$HOME/.local/profiles/"*; fi
}
_home_env_setup; unset -f _home_env_setup
