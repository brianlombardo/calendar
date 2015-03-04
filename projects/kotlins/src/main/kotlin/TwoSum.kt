import org.junit.Assert.assertEquals
import org.junit.Test

class TwoSum {
  private fun twoSumMap(nums: IntArray, target: Int): IntArray {
    val numMap = mutableMapOf<Int, Int>()
    for (i in nums.indices) {
      val complement = target - nums[i]
      if (numMap.containsKey(complement)) {
        numMap[complement]?.let {
          return intArrayOf(it, i)
        }
      } else {
        numMap[nums[i]] = i
      }
    }
    return intArrayOf(-1)
  }

  private fun twoSumBinary(nums: IntArray, target: Int): IntArray {
    var left = 0
    var right = nums.lastIndex
    while (left < right) {
      val sum = nums[left] + nums[right]

      if (sum == target) {
        return intArrayOf(left, right)
      } else if (sum < target) {
        ++left
      } else {
        --right
      }
    }
    return intArrayOf(-1)
  }

  private fun twoSum(nums: IntArray, target: Int): IntArray {
    val map = mutableMapOf<Int, Int>()
    val result = mutableListOf<Int>()

    for (i in nums.indices) {
      map[nums[i]] = i
    }

    for (i in nums.indices) {
      val number = nums[i]
      map[target - number]?.let { complement ->
        if (complement > i) {
          result.add(i)
          map[target - number]?.let {
            result.add(it)
          }
        }
      }
    }

    return result.toIntArray()
  }

  @Test
  fun twoSumMap() {
    val actual = TwoSum().twoSumMap(nums = intArrayOf(2, 7, 11, 15), 9)
    assertEquals(0, actual[0])
    assertEquals(1, actual[1])

    val actual1 = TwoSum().twoSumMap(nums = intArrayOf(3, 3), target = 6)
    assertEquals(0, actual1[0])
    assertEquals(1, actual1[1])

    val actual2 = TwoSum().twoSumMap(nums = intArrayOf(2, 3, 4), target = 6)
    assertEquals(0, actual2[0])
    assertEquals(2, actual2[1])

    val actual3 = TwoSum().twoSumMap(nums = intArrayOf(3, 2, 4), target = 6)
    assertEquals(1, actual3[0])
    assertEquals(2, actual3[1])
  }

  @Test
  fun twoSumBin() {
    val actual = TwoSum().twoSumBinary(nums = intArrayOf(2, 7, 11, 15), 9)
    assertEquals(0, actual[0])
    assertEquals(1, actual[1])

    val actual0 = TwoSum().twoSumBinary(nums = intArrayOf(2, 7, 11, 15), 22)
    assertEquals(1, actual0[0])
    assertEquals(3, actual0[1])

    val actual1 = TwoSum().twoSumBinary(nums = intArrayOf(3, 3), target = 6)
    assertEquals(0, actual1[0])
    assertEquals(1, actual1[1])

    val actual2 = TwoSum().twoSumBinary(nums = intArrayOf(2, 3, 4), target = 6)
    assertEquals(0, actual2[0])
    assertEquals(2, actual2[1])
  }

  @Test
  fun twoSum() {
    val actual = TwoSum().twoSum(nums = intArrayOf(2, 7, 11, 15), 9)
    assertEquals(0, actual[0])
    assertEquals(1, actual[1])

    val actual0 = TwoSum().twoSum(nums = intArrayOf(2, 7, 11, 15), 22)
    assertEquals(1, actual0[0])
    assertEquals(3, actual0[1])

    val actual1 = TwoSum().twoSum(nums = intArrayOf(3, 3), target = 6)
    assertEquals(0, actual1[0])
    assertEquals(1, actual1[1])

    val actual2 = TwoSum().twoSum(nums = intArrayOf(2, 3, 4), target = 6)
    assertEquals(0, actual2[0])
    assertEquals(2, actual2[1])
  }
}