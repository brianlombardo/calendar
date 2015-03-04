import ListNode.Companion.build
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class PalindromeList {
  private fun isPalindrome(head: ListNode?): Boolean {
    if (head == null) return false
    if (head.next == null || head.next?.value == head.value && head.next?.next == null)
      return true

    var node: ListNode = head
    var previous: ListNode = head
    var isPalindrome = false
    while (true) {
      val next = node.next
      if (next == null) {
        if (node.value == head.value) {

          previous.next = null

          head.next?.let { secondItem ->
            isPalindrome = isPalindrome(secondItem)
          }
        }
        break
      } else {
        previous = node
        node = next
      }
    }

    return isPalindrome
  }

  @Test
  internal fun isPalindrome() {
    assertFalse(isPalindrome(null))
    assertTrue(isPalindrome(ListNode(1)))
    assertTrue(isPalindrome(build(1, 2, 3, 2, 1)))
    assertFalse(isPalindrome(build(1, 2, 3, 2, 1, 1)))
    assertTrue(isPalindrome(build(1, 1, 2, 3, 3, 2, 1, 1)))
    assertTrue(isPalindrome(build(1, 1)))
    assertFalse(isPalindrome(build(1, 2)))
  }
}