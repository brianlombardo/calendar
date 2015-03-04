import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayZeros {

  private fun IntArray.moveZeroesRight(): IntArray {
    var index = 0
    var offset = 0

    while (index < size) {
      if (this[index] != 0) {
        if (offset != index) {
          this[offset] = this[index]
        }
        ++offset
      }
      ++index
    }
    while (offset < size) {
      this[offset++] = 0
    }
    return this
  }

  private fun IntArray.moveZeroesLeft(): IntArray {
    var index = lastIndex
    var offset = index

    while (index >= 0) {
      if (this[index] != 0) {
        if (offset != index) {
          this[offset] = this[index]
        }
        --offset
      }
      --index
    }
    while (offset >= 0) {
      this[offset--] = 0
    }
    return this
  }

  @Test
  internal fun `move zeros left`() {
    assertEquals(
      intArrayOf(1, 2, 3, 4, 5).toList(),
      intArrayOf(1, 2, 3, 4, 5).moveZeroesLeft().toList()
    )
    assertEquals(
      intArrayOf(0, 0, 0, 0, 1, 2, 3, 4, 5).toList(),
      intArrayOf(1, 2, 0, 3, 4, 0, 5, 0, 0).moveZeroesLeft().toList()
    )
    assertEquals(
      intArrayOf(0, 0, 1, 2, 3, 4, 5).toList(),
      intArrayOf(0, 0, 1, 2, 3, 4, 5).moveZeroesLeft().toList()
    )
    assertEquals(
      intArrayOf(0, 0, 0, 0, 0, 0, 1).toList(),
      intArrayOf(1, 0, 0, 0, 0, 0, 0).moveZeroesLeft().toList()
    )
  }

  @Test
  internal fun `move zeros right`() {
    assertEquals(
      intArrayOf(1, 2, 3, 4, 5).toList(),
      intArrayOf(1, 2, 3, 4, 5).moveZeroesRight().toList()
    )
    assertEquals(
      intArrayOf(1, 2, 3, 4, 5, 0, 0, 0, 0).toList(),
      intArrayOf(1, 2, 0, 3, 4, 0, 5, 0, 0).moveZeroesRight().toList()
    )
    assertEquals(
      intArrayOf(1, 2, 3, 4, 5, 0, 0).toList(),
      intArrayOf(0, 0, 1, 2, 3, 4, 5).moveZeroesRight().toList()
    )
    assertEquals(
      intArrayOf(1, 0, 0, 0, 0, 0, 0).toList(),
      intArrayOf(1, 0, 0, 0, 0, 0, 0).moveZeroesRight().toList()
    )
  }
}
