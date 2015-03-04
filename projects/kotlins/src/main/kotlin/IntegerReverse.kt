import org.junit.Test
import kotlin.test.assertEquals

class IntegerReverse {
  private fun Int.reverse(): Int {
    var step = this
    var reversed = 0

    while (step != 0) {
      val onesPlace = step % 10
      reversed = (reversed * 10) + onesPlace
      step /= 10
    }

    return reversed
  }

  @Test
  fun reverse() {
    assertEquals(1, 1.reverse())
    assertEquals(-1, (-1).reverse())
    assertEquals(54321, 12345.reverse())
    assertEquals(-54321, (-12345).reverse())
    assertEquals(444, 444.reverse())
    assertEquals(-444, (-444).reverse())
    assertEquals(-4441, (-1444).reverse())
    assertEquals(421, 124.reverse())
  }
}
