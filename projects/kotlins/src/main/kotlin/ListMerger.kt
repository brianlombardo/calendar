import ListNode.Companion.build
import org.junit.Assert.assertEquals
import org.junit.Test


class ListMerger {
  private fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
    var node1 = list1
    var node2 = list2
    if (node1 == null || node2 == null) {
      return node1 ?: node2
    }

    var result = ListNode(-1)
    val resultHead = result
    while (node1 != null && node2 != null) {
      if (node1.value < node2.value) {
        result.next = node1
        node1 = node1.next
      } else {
        result.next = node2
        node2 = node2.next
      }
      result.next?.let { result = it }
    }

    node1?.let { result.next = it }
    node2?.let { result.next = it }

    return resultHead.next
  }

  @Test
  fun mergeTwoLists() {
    assertEquals(build(1, 1, 2, 3, 4, 4), mergeTwoLists(build(1, 2, 4), build(1, 3, 4)))
    assertEquals(null, mergeTwoLists(null, null))
    assertEquals(build(0), mergeTwoLists(null, build(0)))
  }
}
