package trnqilo

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.lang.Runtime.getRuntime

data class ShellCommand(val command: String, val workingDir: String) : Command {
  override fun execute(): String {
    try {
      getRuntime()
        .exec(command.split(" ").toTypedArray(), null, File(workingDir))
        .apply {
          var output: String
          BufferedReader(InputStreamReader(inputStream)).apply {
            output = readText()
            close()
          }
          waitFor()
          var message = output.ifBlank { "" }
          exitValue().let { value ->
            if (value != 0) {
              message += "\nexit $value"
            }
          }
          return message.trim()
        }
    } catch (e: Exception) {
      return e.errorMessage()
    }
  }
}
