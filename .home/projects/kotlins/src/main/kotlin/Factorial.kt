import org.junit.Assert.assertEquals
import org.junit.Test

class Factorial {
  private tailrec fun factorial(n: Long, result: Long = 1): Long =
    if (n > 1) factorial(n - 1, n * result) else result

  @Test
  fun factorial() {
    assertEquals(1, factorial(1))
    assertEquals(2, factorial(2))
    assertEquals(6, factorial(3))
    assertEquals(24, factorial(4))
    assertEquals(120, factorial(5))
  }
}