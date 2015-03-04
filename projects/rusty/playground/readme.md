# Rusty Playground

## setup

### environment

```bash
curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh # install rust
playground/server/db.sh setup # create postgres db
cp playground/server/nginx.conf /location/of/nginx/confs # configure nginx
```

### server

an API server using _Axum_ and _Diesel_ offering POST, GET and DELETE endpoints at '/playground'

```bash
cd playground/server && cargo run
```

### cli

a cli for the playground/client library (provides calls to server APIs using _Reqwest_)

```bash
cd playground/cli
cargo run # get playgrounds
cargo run post # create default playground
cargo run post '{"equipment": ["Swing","Swing"]}'
cargo run delete
```

### web client

a web frontend for the client library using _Seed-Rs_

```bash
cd playground/web

# static host
cargo make verify
cargo make build_release
python3 -m 'http.server' '8000'

# dev server
cargo make serve
cargo make watch

open http://localhost
```
