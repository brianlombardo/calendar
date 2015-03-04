setup database

```shell
# createuser item_user --createdb
# createdb itemlist -U item_user
psql postgres # -U item_user
```

```postgresql
CREATE ROLE item_user WITH LOGIN PASSWORD 'item_pass';
ALTER ROLE item_user CREATEDB;
CREATE DATABASE itemlist;
GRANT ALL PRIVILEGES ON DATABASE itemlist TO item_user;
```

start database

```shell
serv up postgresql@14
```

start server

```shell
npm run start
```

test apis

```shell
curl -X POST 'http://localhost:3001/items' \
   -H 'Content-Type: application/json' \
   -d '{"data": "Hello World"}'
```

```shell
curl -X GET 'http://localhost:3001/items'
```

```shell
curl -X GET 'http://localhost:3001/items/1'
```

```shell
curl -X GET 'http://localhost:3001/items/search?q=Hello'
```

```shell
curl -X PUT 'http://localhost:3001/items/1' \
   -H 'Content-Type: application/json' \
   -d '{"data": "Updated String"}'
```

```shell
curl -X DELETE 'http://localhost:3001/items/1'
```
