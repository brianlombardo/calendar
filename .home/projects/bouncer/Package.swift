// swift-tools-version: 5.10

import PackageDescription

let package = Package(
  name: "Bouncer",
  dependencies: [
    .package(url: "https://github.com/apple/swift-nio.git", from: "2.0.0"),
  ],
  targets: [
    .target(name: "lib"),
    .executableTarget(
      name: "server",
      dependencies: [
        "lib",
        .product(name: "NIOCore", package: "swift-nio"),
        .product(name: "NIOPosix", package: "swift-nio")
      ]
    ),
    .executableTarget(
      name: "client",
      dependencies: [
        "lib",
        .product(name: "NIOCore", package: "swift-nio"),
        .product(name: "NIOPosix", package: "swift-nio")
      ]
    ),
  ]
)
