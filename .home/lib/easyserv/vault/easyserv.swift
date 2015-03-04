#!/usr/bin/env swift

import Vapor
import Foundation

struct Endpoint: Codable {
  let path: String
  let method: String
  let response: String
}

let app = try Application(.detect())

let jsonData = try Data(contentsOf: URL(fileURLWithPath: "server.json"))
let endpoints = try JSONDecoder().decode([Endpoint].self, from: jsonData)

for endpoint in endpoints {
  app.on(HTTPMethod(rawValue: endpoint.method), endpoint.path) { req -> String in
    return endpoint.response
  }
}

try app.run()
