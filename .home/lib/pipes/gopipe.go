//usr/bin/env true; exec $GOROOT/bin/go run "$0" $@
package main

import (
  "bufio"
  "os"
  "fmt"
)

func main() {
  var pipeBytes []byte
  stdin := bufio.NewScanner(os.Stdin)
  for stdin.Scan() {
    pipeBytes = append(pipeBytes, stdin.Bytes()...)
    pipeBytes = append(pipeBytes, byte('\n'))
  }
  fmt.Printf("%s\n", pipeBytes)
  
  args := os.Args[1:]
  for _, arg := range args {
    fmt.Println(arg)
  }
}
