#!/usr/bin/env node

const shell = require('child_process').execSync
shell('npm i -g express body-parser')

const express = require('express')().use(require('body-parser').json())
const server = JSON.parse(require('fs').readFileSync(process.argv[2] ?? 'server.json', 'utf8'))
const root = server.root ?? '', port = server.port ?? 8080

for (const api of server.apis)
  express[(api.method ?? 'get').toLowerCase()](`${root}${api.route}` ?? '/', (req, res) => {
    let statusCode = api.status ?? 200
    console.log(`${new Date().toLocaleTimeString()} ${statusCode} ${req.method} ${req.originalUrl}`)
    api.shell && shell(api.shell)
    api.eval && eval(api.eval)
    api.import && import(`${process.cwd()}/${api.import}`)
    res.status(statusCode)
      .send(api.response ?? { params: req.params ?? {}, query: req.query ?? {}, body: req.body ?? {} })
  })

express.listen(port, () => console.log(`listening on ${port}`))
