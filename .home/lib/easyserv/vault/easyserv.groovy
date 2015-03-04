#!/usr/bin/env groovy

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import groovy.json.JsonSlurper

import static groovy.json.JsonOutput.toJson

def server = new JsonSlurper()
    .parseText(new File(args.length ? args[0] : 'server.json').text)
    as Map<String, String>

def root = server.root ?: ''
def port = server.port.toInteger() ?: 8080

class Api {
  String method, response
  Integer status

  Api(String method, String response, Integer status) {
    this.method = method
    this.response = response
    this.status = status
  }
}

LinkedHashMap<GString, Api> apis = [:]
server.apis.each { api ->
  apis["$root${api.route ?: '/'}"] = new Api(
      api.method ?: 'get',
      api.with { response instanceof Map ? toJson(response) : response },
      api.status ?: 200)
}

void respond(HttpExchange context, int code, String response) {
  context.responseHeaders.add 'Content-type', 'text/plain'
  context.sendResponseHeaders code, 0
  context.responseBody.withWriter { it << response }
  println("${new Date().toLocalDateTime()} $code $context.requestMethod $context.requestURI")
}

HttpServer.create(new InetSocketAddress(port), 0).tap {
  createContext('/') { context ->
    def api = apis[context.requestURI.toString()]
    if (api && context.requestMethod.equalsIgnoreCase(api.method)) {
        respond(context, api.status,api.response)
    } else {
        respond(context, 404,'not found.')
    }
  }
  println "listening on $port"
  start()
}

static void main(String[] args) {}

