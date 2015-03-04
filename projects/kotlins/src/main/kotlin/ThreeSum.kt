import org.junit.Assert.assertEquals
import org.junit.Test


class ThreeSum {
  private fun threeSumBrute(nums: IntArray): List<List<Int>> {
    if (nums.size < 3) {
      return emptyList()
    }
    nums.sort()
    val result = mutableSetOf<List<Int>>()
    for (i in nums.indices) {
      for (j in i + 1 until nums.size)
        for (k in j + 1 until nums.size)
          if (nums[i] + nums[j] + nums[k] == 0)
            result.add(listOf(nums[i], nums[j], nums[k]))
    }

    return result.toList()
  }

  private fun threeSumMap(nums: IntArray): List<List<Int>> {
    val set = mutableSetOf<List<Int>>()
    val map = mutableMapOf<Int, Int>()
    nums.sort()
    for (i in nums.indices) {
      map[nums[i]] = i
    }
    for (i in nums.indices) {
      val num = nums[i]
      for (j in i + 1 until nums.size) {
        val complement = nums[j]
        val target = 0 - num - complement
        map[target]?.let { targetIndex ->
          if (map.containsKey(target) && targetIndex > j) {
            set.add(listOf(num, complement, target))
          }
        }
      }
    }
    return set.toList()
  }

  @Test
  internal fun testThreeSumBrute() {
    val testObject = ThreeSum()
    assertEquals(emptyList<List<Int>>(), testObject.threeSumBrute(intArrayOf()))
    assertEquals(emptyList<List<Int>>(), testObject.threeSumBrute(intArrayOf(0)))
    assertEquals(
      listOf(listOf(-1, -1, 2), listOf(-1, 0, 1)),
      testObject.threeSumBrute(intArrayOf(-1, 0, 1, 2, -1, -4))
    )

    assertEquals(
      listOf(listOf(-1, -1, 2)),
      testObject.threeSumBrute(intArrayOf(-1, -1, 2, 2, -1, -1, 2))
    )

    assertEquals(
      listOf(listOf(-6, 3, 3), listOf(-4, 1, 3), listOf(-1, -1, 2), listOf(-1, 0, 1)),
      testObject.threeSumBrute(intArrayOf(-1, 0, 1, 2, -1, -4, -6, 3, 3))
    )
  }

  @Test
  internal fun testThreeSumMap() {
    val testObject = ThreeSum()
    assertEquals(emptyList<List<Int>>(), testObject.threeSumMap(intArrayOf()))
    assertEquals(emptyList<List<Int>>(), testObject.threeSumMap(intArrayOf(0)))

    assertEquals(
      listOf(listOf(-1, -1, 2), listOf(-1, 0, 1)),
      testObject.threeSumMap(intArrayOf(-1, 0, 1, 2, -1, -4))
    )

    assertEquals(
      listOf(listOf(-6, 3, 3), listOf(-4, 1, 3), listOf(-1, -1, 2), listOf(-1, 0, 1)),
      testObject.threeSumMap(intArrayOf(-1, 0, 1, 2, -1, -4, -6, 3, 3))
    )
  }
}