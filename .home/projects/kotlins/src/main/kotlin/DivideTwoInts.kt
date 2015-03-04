import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.abs

class DivideTwoInts {
  private fun divide(dividend: Int, divisor: Int): Int {
    val sign = if ((dividend < 0) xor (divisor < 0)) -1 else 1

    var dividendValue = abs(dividend)
    val divisorValue = abs(divisor)

    if (divisorValue == 1) return dividendValue * sign

    var quotient = 0
    while (dividendValue >= divisorValue) {
      ++quotient
      dividendValue -= divisorValue
    }
    return quotient * sign
  }

  @Test
  fun testDivide() {
    assertEquals(2, divide(10, 5))
    assertEquals(3, divide(6, 2))
    assertEquals(3, divide(10, 3))
    assertEquals(-3, divide(10, -3))
    assertEquals(5, divide(-10, -2))
    assertEquals(-10, divide(-10, 1))
  }
}
