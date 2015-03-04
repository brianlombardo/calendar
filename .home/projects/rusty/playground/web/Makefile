generate:
	cargo install cargo-make
	cargo install cargo-generate
	# cargo generate --git https://github.com/seed-rs/seed-quickstart.git --name web
clean:
	cargo clean
build:
	cargo make verify
	cargo make build_release
host:
	echo 'http://localhost'
	python3 -m 'http.server' '8000'
serve:
	cargo make serve
watch:
	echo 'http://localhost'
	cargo make watch
