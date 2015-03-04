import org.junit.Assert.*
import org.junit.Test
import java.util.*


class TreeOps {
  private fun inorderTraversal(root: TreeNode?): List<Int> =
    mutableListOf<Int>().apply {
      root?.traverse { add(it) }
    }

  private fun isSymmetric(root: TreeNode?, recursion: Boolean = true): Boolean =
    if (recursion) accumulateSymmetricEqualities(root?.left, root?.right)
    else iterateSymmetricEqualities(root?.left, root?.right)

  private fun accumulateSymmetricEqualities(left: TreeNode?, right: TreeNode?): Boolean =
    left == null && right == null ||
        left?.value == right?.value &&
        accumulateSymmetricEqualities(left?.left, right?.right) &&
        accumulateSymmetricEqualities(left?.right, right?.left)

  private fun iterateSymmetricEqualities(leftNode: TreeNode?, rightNode: TreeNode?): Boolean {
    val leftStack = Stack<TreeNode?>()
    val rightStack = Stack<TreeNode?>()
    leftStack.push(leftNode)
    rightStack.push(rightNode)
    while (leftStack.size > 0) {
      val left = leftStack.pop()
      val right = rightStack.pop()
      if (left == null && right == null) {
        continue
      }
      if (left != null && right != null && left.value == right.value) {
        leftStack.push(left.left)
        rightStack.push(right.right)
        leftStack.push(left.right)
        rightStack.push(right.left)
        continue
      }
      return false
    }
    return true
  }

  @Test
  fun inorderTraversal() {
    assertEquals(
      listOf(1), inorderTraversal(TreeNode(1))
    )
    assertEquals(
      emptyList<Int>(), inorderTraversal(null)
    )
    assertEquals(
      listOf(1, 3, 2),
      inorderTraversal(
        TreeNode(
          1,
          right = TreeNode(
            2,
            left = TreeNode(3)
          )
        )
      )
    )
    assertEquals(
      listOf(1, 2, 3, 4, 5, 6, 21),
      inorderTraversal(
        TreeNode(
          4,
          left = TreeNode(2, left = TreeNode(1), right = TreeNode(3)),
          right = TreeNode(
            6, left = TreeNode(5), right = TreeNode(21)
          )
        )
      )
    )
  }

  @Test
  fun isSymmetrical() {
    listOf(true, false).forEach {
      assertTrue(isSymmetric(TreeNode(0, TreeNode(1), TreeNode(1)), it))
      assertFalse(isSymmetric(TreeNode(0, TreeNode(2), TreeNode(1)), it))
      assertTrue(
        isSymmetric(
          TreeNode(
            0,
            TreeNode(
              1,
              TreeNode(5, TreeNode(4), TreeNode(3)),
              TreeNode(7, TreeNode(3), TreeNode(4))
            ),
            TreeNode(
              1,
              TreeNode(7, TreeNode(4), TreeNode(3)),
              TreeNode(5, TreeNode(3), TreeNode(4))
            )
          ), it
        )
      )
    }
  }
}