import NIOCore
import NIOPosix
import lib

class MultiEchoHandler: ChannelInboundHandler {
  typealias InboundIn = ByteBuffer
  typealias OutboundOut = ByteBuffer

  static var clients: [Channel] = []

  func channelActive(context: ChannelHandlerContext) {
    MultiEchoHandler.clients.append(context.channel)

    print("Connected \(context.remoteAddress?.ipAddress?.lowercased() ?? "??")")
  }

  func channelInactive(context: ChannelHandlerContext) {
    if let index = MultiEchoHandler.clients.firstIndex(where: { $0 === context.channel }) {
      MultiEchoHandler.clients.remove(at: index)
      print("Disconnected \(context.remoteAddress?.ipAddress?.lowercased() ?? "??")")
    }
  }

  func channelRead(context: ChannelHandlerContext, data: NIOAny) {
    var buffer = self.unwrapInboundIn(data)
    for client in MultiEchoHandler.clients where client !== context.channel {
      client.writeAndFlush(self.wrapOutboundOut(buffer), promise: nil)
    }
    print("\(context.channel.remoteAddress?.ipAddress?.lowercased() ?? "??"): \(buffer.readString(length: buffer.readableBytes) ?? "??")")
  }
}

func bind(to port: Int, on eventLoopGroup: EventLoopGroup) -> EventLoopFuture<Channel> {
  let bootstrap = ServerBootstrap(group: eventLoopGroup)
    .serverChannelOption(ChannelOptions.backlog, value: 256)
    .serverChannelOption(ChannelOptions.socketOption(.so_reuseaddr), value: 1)
    .childChannelInitializer { channel in
      channel.pipeline.addHandler(MultiEchoHandler())
    }
    .childChannelOption(ChannelOptions.socketOption(.so_reuseaddr), value: 1)
    .childChannelOption(ChannelOptions.maxMessagesPerRead, value: 16)

  return bootstrap.bind(host: "localhost", port: port)
}

func serve() {
  let group = MultiThreadedEventLoopGroup(numberOfThreads: System.coreCount)
  defer {
    try! group.syncShutdownGracefully()
  }

  let ports = getPorts()

  do {
    let channels = try ports.map { port in
      try bind(to: port, on: group).wait()
    }
    
    for channel in channels {
      print("Listening on \(channel.localAddress!)")
    }
    
    let _ = try EventLoopFuture.whenAllComplete(channels.map { $0.closeFuture }, on: group.next()).wait()
  } catch {
    print("Error: \(error)")
    exit(1)
  }
}

serve()
