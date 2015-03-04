import NIOCore
import NIOPosix
import lib

class ClientHandler: ChannelInboundHandler {
  typealias InboundIn = ByteBuffer
  typealias OutboundOut = ByteBuffer

  func channelActive(context: ChannelHandlerContext) {
    print("Connected to server")
    sendMessage(context: context, message: "Hello, Server!")
  }

  func channelRead(context: ChannelHandlerContext, data: NIOAny) {
    var buffer = unwrapInboundIn(data)
    let receivedString = buffer.readString(length: buffer.readableBytes)
    print("RX \(receivedString ?? "failed to read")")
  }

  func sendMessage(context: ChannelHandlerContext, message: String) {
    var buffer = context.channel.allocator.buffer(capacity: message.utf8.count)
    buffer.writeString(message)
    context.writeAndFlush(self.wrapOutboundOut(buffer), promise: nil)
    print("TX \(message)")
  }
}

func connect () {
  let group = MultiThreadedEventLoopGroup(numberOfThreads: 1)
  defer {
    try! group.syncShutdownGracefully()
  }

  let bootstrap = ClientBootstrap(group: group)
    .channelOption(ChannelOptions.socketOption(.so_reuseaddr), value: 1)
    .channelInitializer { channel in
      channel.pipeline.addHandler(ClientHandler())
    }

  let port = getPort()
  do {
    let channel = try bootstrap.connect(host: "localhost", port: port).wait()
    print("Client connected to \(channel.remoteAddress?.ipAddress?.lowercased() ?? "??")")

    try channel.closeFuture.wait()
  } catch {
    print("Error: \(error)")
    exit(1)
  }
}

connect()
