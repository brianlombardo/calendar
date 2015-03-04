import org.junit.Test
import kotlin.test.assertEquals

class TreeToList {

  private fun treeToList(root: TreeNode): ListNode? {
    var head: ListNode? = null
    var tail: ListNode? = null
    root.traverse { value ->
      val listNode = ListNode(value)
      if (head == null) {
        head = listNode
        tail = head
      } else {
        tail?.next = listNode
        tail = tail?.next
      }
    }
    return head
  }

  @Test
  fun `test list from single node tree`() {
    assertEquals(ListNode(0), treeToList(TreeNode(0)))
  }

  @Test
  fun `test list from multi node tree`() {
    val oneTwoThreeTree = TreeNode(2, left = TreeNode(1), right = TreeNode(3))
    val oneTwoThreeList = ListNode(1, next = ListNode(2, next = ListNode(3)))
    assertEquals(
      oneTwoThreeList,
      treeToList(oneTwoThreeTree)
    )
  }
}