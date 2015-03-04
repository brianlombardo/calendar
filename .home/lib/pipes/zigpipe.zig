///usr/bin/env true; exec blobman zig "$0" $@

const std = @import("std");

pub fn main() !void {
  var stdin = std.io.getStdIn().reader();
  var buf: [100]u8 = undefined;
  while (try stdin.readUntilDelimiterOrEof(&buf, '\n')) |line| {
    std.debug.print("{s}\n", .{line});
  }
}
