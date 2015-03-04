#!/usr/bin/env bash
///usr/bin/env true; exec blobman kotlin "$0" $@

fun main(args: Array<String>) {
  while (true) {
    readLine()?.let {
      println(it)
    } ?: break
  }

  for (item in args) { println(item) }
}
