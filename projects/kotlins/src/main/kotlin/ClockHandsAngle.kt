import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.abs

class ClockHandsAngle {

  private fun getAngleDifference(hours: Int, minutes: Int): Double {
    val minutesDegrees = minutes * 6.0

    val hoursAdditionalRotation = (minutes / 60.0) * 30.0
    val hoursDegrees = (hours * 30.0) + hoursAdditionalRotation

    return abs(minutesDegrees - hoursDegrees)
  }

  @Test
  fun testAngleDifference() {
    assertEquals(277.5, ClockHandsAngle().getAngleDifference(12, 15), 0.001)
    assertEquals(5.5, ClockHandsAngle().getAngleDifference(11, 59), 0.001)
  }
}

