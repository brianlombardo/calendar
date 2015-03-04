import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

sealed class BadVersionResult

data class BadVersionFound(val version: Int) : BadVersionResult()
object NoBadVersionFound : BadVersionResult()


class BadVersionService {
  var firstBadVersion = -1
  fun isBadVersion(version: Int): Boolean = version >= firstBadVersion
}

val service = BadVersionService()

class BadVersionResolver {
  private fun firstBadVersion(maxVersion: Int): BadVersionResult {
    var right = maxVersion
    var left = 1
    if (!service.isBadVersion(maxVersion)) {
      return NoBadVersionFound
    }
    while (left < right) {
      val mid = left + (right - left) / 2
      if (service.isBadVersion(mid)) {
        right = mid
      } else {
        left = mid + 1
      }
    }
    return BadVersionFound(left)
  }

  @Test
  fun `test the resolver`() {
    val maxVersion = Random(100).nextInt()
    service.firstBadVersion = maxVersion + 1
    assertEquals(
      NoBadVersionFound,
      BadVersionResolver()
        .firstBadVersion(maxVersion)
    )

    for (i in 1..maxVersion) {
      service.firstBadVersion = i
      assertEquals(
        BadVersionFound(i),
        BadVersionResolver()
          .firstBadVersion(maxVersion)
      )
    }
  }
}
