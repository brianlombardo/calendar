import org.junit.Assert.assertEquals
import org.junit.Test

class ZigZagConverter {
  private val testString = "PAYPALISHIRING"
  private fun convertMap(numRows: Int, string: String = testString): String {
    val results = mutableMapOf<Int, String>()
    var currentRow = 0
    var down = true

    for (i in string.indices) {
      results[currentRow] = (results[currentRow] ?: "") + string[i]

      if (down) ++currentRow
      else --currentRow
      if (currentRow == 0 || currentRow == numRows - 1) {
        down = !down
      }
    }

    var result = ""
    results.keys.forEach { result += results[it] }

    return result
  }

  private fun convertArray(numRows: Int, string: String = testString): String {
    val results = Array(numRows) { "" }
    var currentRow = 0
    var down = true

    for (i in string.indices) {
      results[currentRow] = results[currentRow] + string[i]

      if (down) ++currentRow
      else --currentRow
      if (currentRow == 0 || currentRow == numRows - 1) {
        down = !down
      }
    }

    var result = ""
    results.forEach { result += it }

    return result
  }


  @Test
  internal fun testMap() {
    assertEquals("PYAIHRNAPLSIIG", convertMap(2))
    assertEquals("PAHNAPLSIIGYIR", convertMap(3))
    assertEquals("PINALSIGYAHRPI", convertMap(4))
    assertEquals("PHASIYIRPLIGAN", convertMap(5))
  }

  @Test
  internal fun testArray() {
    assertEquals("PYAIHRNAPLSIIG", convertArray(2))
    assertEquals("PAHNAPLSIIGYIR", convertArray(3))
    assertEquals("PINALSIGYAHRPI", convertArray(4))
    assertEquals("PHASIYIRPLIGAN", convertArray(5))
  }
}