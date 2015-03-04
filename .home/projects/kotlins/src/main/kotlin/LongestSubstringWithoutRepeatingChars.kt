import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.max

class LongestSubstringWithoutRepeatingChars {
  private fun lengthOfLongestSubstring(s: String): Int {
    val length = s.length
    if (length == 1 || length == 0) return length


    val chars = mutableSetOf<Char>()

    var left = 0
    var result = 0

    for (right in s.indices) {
      while (s[right] in chars) {
        chars.remove(s[left++])
      }
      chars.add(s[right])
      result = max(result, right - left + 1)
    }

    return result
  }

  private fun getLongestSubstring(s: String): String {
    val visited = mutableMapOf<Char, Int>()
    var output = ""
    var start = 0
    var end = 0
    while (end < s.length) {
      val current = s[end]
      if (visited.containsKey(current)) {
        visited[current]?.let {
          start = max(start, it.plus(1))
        }
      }
      if (output.length < end - start + 1) {
        output = s.substring(start, end + 1)
      }
      visited[current] = end
      ++end
    }
    return output
  }

  @Test
  fun test() {
    assertEquals(3, getLongestSubstring("abcabcbb").length)
    assertEquals(1, getLongestSubstring("bbbbb").length)
    assertEquals(3, getLongestSubstring("pwwkew").length)
    assertEquals(1, getLongestSubstring("a").length)
    assertEquals(0, getLongestSubstring("").length)
  }

  @Test
  fun test1() {
    assertEquals(3, lengthOfLongestSubstring("abcabcbb"))
    assertEquals(1, lengthOfLongestSubstring("bbbbb"))
    assertEquals(3, lengthOfLongestSubstring("pwwkew"))
    assertEquals(1, lengthOfLongestSubstring("a"))
    assertEquals(0, lengthOfLongestSubstring(""))
  }
}
