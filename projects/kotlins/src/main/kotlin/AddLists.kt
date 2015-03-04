import ListNode.Companion.build
import org.junit.Assert.assertEquals
import org.junit.Test

class AddLists {

  private fun addLists(l1: ListNode?, l2: ListNode?): ListNode? {
    if (l1 == null && l2 == null) {
      return null
    }
    if (l1 == null) {
      return l2
    }
    if (l2 == null) {
      return l1
    }

    var l1Tail: ListNode? = l1
    var l2Tail: ListNode? = l2
    var result: ListNode? = null
    var resultTail: ListNode? = null
    var carry = 0

    while (l1Tail != null || l2Tail != null || carry == 1) {
      var digit = carry
      l1Tail?.let {
        digit += it.value
      }
      l2Tail?.let {
        digit += it.value
      }
      if (digit > 9) {
        carry = 1
        digit -= 10
      } else {
        carry = 0
      }

      if (resultTail == null) {
        resultTail = ListNode(digit)
        result = resultTail
      } else {
        resultTail.next = ListNode(digit)
        resultTail = resultTail.next
      }

      l1Tail = l1Tail?.next
      l2Tail = l2Tail?.next
    }

    return result
  }

  @Test
  internal fun `zero plus zero`() {
    assertEquals(build(0), addLists(build(0), build(0)))
  }

  @Test
  internal fun `342 plus 465`() {
    assertEquals(
      build(7, 0, 8), addLists(
        build(2, 4, 3),
        build(5, 6, 4)
      )
    )
  }

  @Test
  internal fun `3412 plus 4615`() {
    assertEquals(
      build(7, 2, 0, 8), addLists(
        build(2, 1, 4, 3),
        build(5, 1, 6, 4)
      )
    )
  }

  @Test
  internal fun `33341201 plus 461512`() {
    assertEquals(
      build(3, 1, 7, 2, 0, 8, 3, 3), addLists(
        build(1, 0, 2, 1, 4, 3, 3, 3),
        build(2, 1, 5, 1, 6, 4)
      )
    )
  }

  @Test
  internal fun `9999999 plus 9999`() {
    val actual = addLists(build(9, 9, 9, 9, 9, 9, 9), build(9, 9, 9, 9))
    assertEquals(build(8, 9, 9, 9, 0, 0, 0, 1), actual)
  }
}
