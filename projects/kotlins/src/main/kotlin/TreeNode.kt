data class TreeNode(
  var value: Int,
  var left: TreeNode? = null,
  var right: TreeNode? = null
)

fun TreeNode.traverse(action: (input: Int) -> Unit = { input: Int -> println(input) }) {
  left?.traverse(action)
  action.invoke(value)
  right?.traverse(action)
}
