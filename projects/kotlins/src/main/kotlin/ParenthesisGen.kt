import org.junit.Assert.assertEquals
import org.junit.Test

class ParenthesisGen {
  private fun generateParens(n: Int): List<String> {
    val strings = mutableListOf<String>()
    backtrack(strings, "", 0, 0, n)
    return strings
  }

  private fun backtrack(
    strings: MutableList<String>,
    parens: String,
    openParens: Int,
    closedParens: Int,
    max: Int
  ) {
    if (max * 2 == parens.length) {
      strings.add(parens)
    }

    if (openParens < max) {
      backtrack(strings, "$parens(", openParens + 1, closedParens, max)
    }
    if (closedParens < openParens) {
      backtrack(strings, "$parens)", openParens, closedParens + 1, max)
    }
  }

  @Test
  fun generateParens() {
    assertEquals(listOf("()"), generateParens(1))
    assertEquals(
      listOf("(())", "()()"),
      generateParens(2)
    )
    assertEquals(
      listOf("((()))", "(()())", "(())()", "()(())", "()()()"),
      generateParens(3)
    )
  }
}

