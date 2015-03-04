import org.junit.Assert.assertEquals
import org.junit.Test

class PermutationResolver {
  private fun nextLargestPermutation(value: IntArray): IntArray {
    if (value.isNotEmpty()) {
      var i = value.lastIndex

      while (i != 0) {
        val digit = value[i]
        val previousDigit = value[i - 1]
        if (digit > previousDigit) {
          value[i] = previousDigit
          value[i - 1] = digit
          break
        }
        --i
      }
    }
    return value
  }

  @Test
  fun permutations() {
    val emptyList = emptyList<Int>()

    PermutationResolver().apply {
      assertEquals(
        listOf(5, 4, 3, 3, 1, 2),
        nextLargestPermutation(intArrayOf(5, 4, 3, 1, 3, 2)).toList()
      )
      assertEquals(
        listOf(3, 5, 1), nextLargestPermutation(intArrayOf(3, 1, 5)).toList()
      )
      assertEquals(
        listOf(5, 4, 1, 2, 4, 5, 7, 6),
        nextLargestPermutation(intArrayOf(5, 4, 1, 2, 4, 5, 6, 7)).toList()
      )
      assertEquals(
        listOf(9, 8, 7, 6, 5, 4, 3, 2),
        nextLargestPermutation(intArrayOf(9, 8, 7, 6, 5, 4, 3, 2)).toList()
      )
      assertEquals(
        listOf(9, 8, 7, 6, 5, 4, 3, 2),
        nextLargestPermutation(intArrayOf(8, 9, 7, 6, 5, 4, 3, 2)).toList()
      )
      assertEquals(
        listOf(9, 8, 7, 6, 5, 4, 3, 2),
        nextLargestPermutation(intArrayOf(9, 8, 6, 7, 5, 4, 3, 2)).toList()
      )
      assertEquals(
        listOf(1), nextLargestPermutation(intArrayOf(1)).toList()
      )
      assertEquals(
        emptyList, nextLargestPermutation(intArrayOf()).toList()
      )
    }
  }
}

