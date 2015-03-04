package trnqilo

import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import io.mockk.every
import io.mockk.mockk
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TelecomandoTest {
  @Test
  fun shellCommandTest() {
    assertEquals(
      "TelecomandoTest.kt",
      ShellCommand("ls", "src/test/kotlin/trnqilo").execute()
    )
    assertEquals(
      "exit 2",
      ShellCommand("ls nothing", "src/test/kotlin/trnqilo").execute()
    )
  }

  @Test
  fun httpCommandTest() {
    val json = Json { ignoreUnknownKeys = true }
    val contributors = json.decodeFromString<List<Contributor>>(
      HttpCommand("https://api.github.com/repos/trnqilo/home/contributors").execute()
    )
    val login = contributors[0].login
    assertEquals(1, contributors.size)
    assertEquals("trnqilo", login)
  }

  @Test
  fun sshCommandTest() {
    val jSch = mockk<JSch>()
    val session = mockk<Session>()
    every { jSch.getSession(any()) } returns session
    assertEquals("hello", SshCommand("echo hello", jSch = jSch).execute())
  }
}

@Serializable
data class Contributor(val login: String)
