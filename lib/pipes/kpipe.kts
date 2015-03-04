#!/usr/bin/env bash
///usr/bin/env true; exec kotlinc -script "$0" $@

while (true) {
  readLine()?.let {
    println(it)
  } ?: break
}

for (item in args) { println(item) }
