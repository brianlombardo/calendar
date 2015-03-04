import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.test.fail

class ListReverse {

  private fun reverse(head: ListNode): ListNode? {
    var tail: ListNode? = head
    var result: ListNode? = null

    while (tail != null) {
      val next = tail.next
      tail.next = result
      result = tail
      tail = next
    }

    return result
  }

  @Test
  fun reverse() {
    ListNode.build(1)?.let {
      assertEquals(it, reverse(it))
    } ?: fail()

    ListNode.build(1, 2, 3, 4, 5, 7, 4)?.let {
      assertEquals(ListNode.build(4, 7, 5, 4, 3, 2, 1), reverse(it))
    } ?: fail()
  }
}