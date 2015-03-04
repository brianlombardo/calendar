package trnqilo

import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import java.io.ByteArrayOutputStream
import java.lang.Thread.sleep
import java.util.*

data class SshCommand(
  val command: String,
  val destination: String = "localhost",
  val port: Int = 22,
  val user: String = "",
  val password: String = "",
  val keyFile: String = "",
  val jSch: JSch = JSch()
) : Command {
  override fun execute(): String {
    var session: Session? = null
    var channel: ChannelExec? = null
    return try {
      val jsch = JSch()
      session = jsch.getSession(user, destination, 22)
      session.setPassword(password)

      val config = Properties()
      config["StrictHostKeyChecking"] = "no"
      session.setConfig(config)

      session.connect()

      channel = session.openChannel("exec") as ChannelExec
      channel.setCommand(command)

      val outputStream = ByteArrayOutputStream()
      channel.outputStream = outputStream

      channel.connect()

      while (!channel.isClosed) {
        sleep(100)
      }

      outputStream.toString()
    } catch (e: Exception) {
      e.errorMessage()
    } finally {
      channel?.disconnect()
      session?.disconnect()
    }
  }

}
