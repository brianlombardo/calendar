#!/usr/bin/env bash
set -e

read -rep "server " server
read -rep "username " username
read -sp "password " password
cargo run $server $username $password
