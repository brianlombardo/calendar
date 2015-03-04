data class ListNode(var value: Int, var next: ListNode? = null) {
  companion object {
    fun build(vararg values: Int): ListNode? {
      if (values.isEmpty()) return null

      val list = ListNode(values[0])
      var node: ListNode? = list

      if (values.size > 1) {
        for (value in 1 until values.size) {
          node?.next = ListNode(values[value])
          node = node?.next
        }
      }

      return list
    }
  }
}
