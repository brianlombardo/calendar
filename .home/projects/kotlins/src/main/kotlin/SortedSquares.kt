import org.junit.Test
import kotlin.math.abs
import kotlin.test.assertEquals

class SortedSquares {

  private fun sortedSquaresPipe(nums: List<Int>): List<Int> = nums.map { it * it }.sorted()

  private fun sortedSquaresLoop(nums: List<Int>): List<Int> {
    val result = MutableList(nums.size) { -1 }
    var left = 0
    var right = nums.lastIndex
    for (index in nums.indices.reversed()) {
      val num = nums[
        if (abs(nums[left]) < abs(nums[right])) {
          right--
        } else {
          left++
        }
      ]
      result[index] = num * num
    }
    return result
  }

  @Test
  fun sortedSquaresPipe() {
    sortedSquaresTest(::sortedSquaresPipe)
  }

  @Test
  fun sortedSquaresLoop() {
    sortedSquaresTest(::sortedSquaresLoop)
  }

  private fun sortedSquaresTest(sortedSquares: (List<Int>) -> List<Int>) {
    assertEquals(listOf(), sortedSquares(listOf()))
    assertEquals(listOf(1, 4, 9), sortedSquares(listOf(-3, 1, 2)))
    assertEquals(listOf(1, 4, 9, 16, 16, 25), sortedSquares(listOf(-5, -4, -3, 1, 2, 4)))
    assertEquals(
      listOf(1, 1, 4, 9, 16, 16, 25, 49, 49, 121, 144),
      sortedSquares(listOf(-11, -7, -5, -4, -3, -1, 1, 2, 4, 7, 12))
    )
  }

}
