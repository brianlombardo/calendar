import junit.framework.TestCase.assertEquals
import org.junit.Test


class BinarySearch {
  private tailrec fun search(
    nums: IntArray,
    target: Int,
    start: Int = 0,
    end: Int = nums.lastIndex
  ): Int {
    if (start > end) return -1
    val midpoint = (start + end) / 2
    val guess = nums[midpoint]
    return when {
      guess < target -> {
        search(nums, target, midpoint + 1, end)
      }

      guess > target -> {
        search(nums, target, start, midpoint - 1)
      }

      else -> {
        midpoint
      }
    }
  }

  @Test
  fun testBinarySearch() {
    assertEquals(4, search(intArrayOf(-1, 0, 3, 5, 9, 12), target = 9))
    assertEquals(-1, search(intArrayOf(-1, 0, 3, 5, 9, 12), target = 2))
    assertEquals(1, search(intArrayOf(-1, 0, 3, 5, 9, 12), target = 0))
    assertEquals(5, search(intArrayOf(-1, 0, 3, 5, 9, 12, 13, 25, 30, 35, 42), target = 12))
    assertEquals(6, search(intArrayOf(-1, 0, 3, 5, 9, 12, 13, 25, 30, 35, 42), target = 13))
    assertEquals(8, search(intArrayOf(-1, 0, 3, 5, 9, 12, 13, 25, 30, 35, 42), target = 30))
    assertEquals(1, search(intArrayOf(-1, 0, 3, 5, 9, 12, 13, 25, 30, 31, 1000), target = 0))
  }
}