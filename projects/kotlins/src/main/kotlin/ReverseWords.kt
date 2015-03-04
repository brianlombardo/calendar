import org.junit.Test
import kotlin.test.assertEquals

class ReverseWords {
  private fun String.reverseWords(): String =
    this.formatSpaces()
      .split(" ")
      .reversed()
      .joinToString(" ")

  private fun String.formatSpaces() = this.trim().replace("\\s+".toRegex(), " ")

  private fun reverseWordsChars(s: String): String {
    var charArray = s.toCharArray()
    var sentenceStart: Int? = null
    var sentenceEnd: Int? = s.lastIndex
    var wordStart: Int? = null
    var wordEnd: Int? = null

    for ((index, c) in charArray.withIndex()) {
      if (c == ' ' && wordStart != null) {
        wordEnd = index - 1
        charArray.reverse(wordStart, wordEnd)
        wordStart = null
      } else if (index == s.lastIndex && wordStart != null) {
        wordEnd = index
        charArray.reverse(wordStart, wordEnd)
      } else {
        if (sentenceStart == null && c != ' ') {
          sentenceStart = index
        }
        if (wordStart == null) {
          wordStart = index
        }
        if (wordEnd != null) {
          wordEnd = null
        }
      }
    }
    if (wordEnd != sentenceEnd) {
      sentenceEnd = wordEnd
    }

    sentenceStart?.let {
      sentenceEnd?.let {
        charArray =
          charArray.slice(sentenceStart..sentenceEnd).toCharArray()
      }
    }

    charArray.reverse()

    return String(charArray)
  }

  private fun CharArray.reverse(start: Int?, end: Int?) {
    var left = start ?: 0
    var right = end ?: lastIndex
    while (left < right) {
      val leftValue = this[left]
      this[left] = this[right]
      this[right] = leftValue
      ++left
      --right
    }
  }

  @Test
  fun `test string reverse`() {
    assertEquals("world hi", "  hi  world ".reverseWords())
    assertEquals("reversed is this", "this is reversed".reverseWords())
    assertEquals("", "".reverseWords())
    assertEquals("hi", "hi".reverseWords())
  }

  @Test
  fun `test char array reverse`() {
    assertEquals("world hi", reverseWordsChars("hi world"))
    assertEquals("reversed is this", reverseWordsChars("this is reversed"))
    assertEquals("", reverseWordsChars(""))
    assertEquals("hi", reverseWordsChars("hi"))
  }
}
