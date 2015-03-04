package trnqilo

interface Command {
  fun execute(): String
}

fun Exception.errorMessage(): String = message ?: "An unknown exception occurred."
