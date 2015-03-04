import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.max
import kotlin.math.min

class ContainerWithMostWater {
  private fun bruteForce(height: IntArray): Int {
    var result = -1

    for (left in height.indices) {
      for (right in left + 1 until height.size) {
        val containerWidth = right - left
        val containerHeight = min(height[left], height[right])
        val area = containerWidth * containerHeight
        result = max(result, area)
      }
    }
    return result
  }

  private fun slidingWindow(height: IntArray): Int {
    var left = 0
    var right = height.lastIndex
    var result = -1

    while (left < right) {
      val containerWidth = right - left
      val leftHeight = height[left]
      val rightHeight = height[right]
      val containerHeight = min(leftHeight, rightHeight)
      val area = containerWidth * containerHeight
      result = max(result, area)

      if (leftHeight == containerHeight) {
        ++left
      } else if (rightHeight == containerHeight) {
        --right
      }
    }

    return result
  }

  @Test
  fun testContainerWithMostWaterBrute() {
    assertEquals(1, bruteForce(intArrayOf(1, 1)))
    assertEquals(49, bruteForce(intArrayOf(1, 8, 6, 2, 5, 4, 8, 3, 7)))
  }

  @Test
  fun testContainerWithMostWaterLinear() {
    assertEquals(1, slidingWindow(intArrayOf(1, 1)))
    assertEquals(49, slidingWindow(intArrayOf(1, 8, 6, 2, 5, 4, 8, 3, 7)))
  }
}
