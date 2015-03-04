import ListNode.Companion.build
import org.junit.Assert.assertEquals
import org.junit.Test

class ListNodeRemover {

  private fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
    if (head?.next == null) {
      return null
    }

    var node: ListNode? = head

    data class ListNodePointer(var node: ListNode? = null)

    val pointer = ListNodePointer(head)

    var listSize = 0
    while (node != null) {
      val next = node.next

      if (listSize >= n && next != null) {
        pointer.node = pointer.node?.next
      }

      ++listSize
      node = next
    }

    if (listSize < n) {
      return null
    } else if (listSize == n) {
      return head.next
    }
    pointer.node?.next = pointer.node?.next?.next

    return head
  }

  @Test
  fun test() {
    ListNodeRemover().apply {
      assertEquals(null, removeNthFromEnd(build(1), 1))
      assertEquals(null, removeNthFromEnd(build(1, 2, 3, 4, 5), 6))
      assertEquals(build(1), removeNthFromEnd(build(1, 2), 1))
      assertEquals(build(1, 2, 3, 5), removeNthFromEnd(build(1, 2, 3, 4, 5), 2))
      assertEquals(build(1, 3, 4, 5), removeNthFromEnd(build(1, 2, 3, 4, 5), 4))
      assertEquals(build(1, 2, 4, 5, 6), removeNthFromEnd(build(1, 2, 3, 4, 5, 6), 4))

      assertEquals(build(2, 3), removeNthFromEnd(build(1, 2, 3), 3))
      assertEquals(build(2, 3, 4, 5), removeNthFromEnd(build(1, 2, 3, 4, 5), 5))
    }

  }
}