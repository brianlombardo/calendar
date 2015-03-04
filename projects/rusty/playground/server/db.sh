#!/usr/bin/env bash
set -e

db_url="postgresql://playground:playground@localhost:5432/playground"

function _setup {
  psql postgres -c "create role playground with login password 'playground';"
  psql postgres -c "alter role playground createdb;"
  psql postgres -U playground -c "create database playground;"
}

function _init {
  rm -frv src/db/migrations
  mkdir src/db/migrations

  diesel setup --database-url "$db_url"
  diesel migration generate create_playground --database-url "$db_url"

  echo "DROP TABLE IF EXISTS playground;
CREATE TABLE playground
(
  id         BIGSERIAL PRIMARY KEY,
  play_space VARCHAR NOT NULL
);
" > src/db/migrations/*_create_playground/up.sql

  echo 'DROP TABLE playground;' > src/db/migrations/*_create_playground/down.sql

  diesel migration run --database-url "$db_url"
}

function _run {
  diesel migration run --database-url "$db_url"
}

function _redo {
  diesel migration redo --database-url "$db_url"
}

function _schema {
  schema_rs="$(diesel print-schema --database-url "$db_url")"
  echo "$schema_rs" > src/db/schema.rs
}

function _trigger_listener {
  psql postgres -U playground -c "CREATE OR REPLACE FUNCTION playground_update() RETURNS trigger AS
\$$
DECLARE
   id    int;
   key   varchar;
   value varchar;
BEGIN
   IF TG_OP = 'INSERT' OR TG_OP = 'UPDATE' THEN
       id = NEW.id;
       key = NEW.key;
       value = NEW.val;
   ELSE
       id = OLD.id;
       key = OLD.key;
       value = OLD.val;
   END IF;
   PERFORM pg_notify('playground',
                     json_build_object('table', TG_TABLE_NAME, 'id', id, 'key', key, 'value',
                                       value, 'action_type', TG_OP)::text);
   RETURN NEW;
END;
\$$ LANGUAGE plpgsql;

-- DROP TRIGGER playground_notify_update ON \"playground\";
CREATE TRIGGER playground_notify_update
   AFTER UPDATE
   ON \"playground\"
   FOR EACH ROW
EXECUTE PROCEDURE playground_update();

-- DROP TRIGGER playground_notify_insert ON \"playground\";
CREATE TRIGGER playground_notify_insert
   AFTER INSERT
   ON \"playground\"
   FOR EACH ROW
EXECUTE PROCEDURE playground_update();

-- DROP TRIGGER playground_notify_delete ON \"playground\";
CREATE TRIGGER playground_notify_delete
   AFTER DELETE
   ON \"playground\"
   FOR EACH ROW
EXECUTE PROCEDURE playground_update();
"
}

_$@
