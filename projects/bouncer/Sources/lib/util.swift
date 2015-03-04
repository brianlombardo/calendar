import Foundation

public func getPort() -> Int {
  guard let portsString = getVar(envVar: "BOUNCER_PORT") else { return 1234 }
  return portsString.int
}

public func getPorts() -> [Int] {
  guard let portsString = getVar(envVar: "BOUNCER_PORTS") else { return [1234] }
  let ports = portsString.split(separator: " ").compactMap { "\($0)".int }
  return ports.isEmpty ? [1234] : ports
}

public func getProto() -> String {
  return getVar(envVar: "PROTO")?.lowercased() ?? "tcp"
}

private func getVar(envVar: String) -> String? {
  return ProcessInfo.processInfo.environment[envVar]
}

private extension String {
  var int: Int { return Int(self.trimmingCharacters(in: .whitespaces)) ?? 1234 }
}
