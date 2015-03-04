import junit.framework.TestCase.assertEquals
import org.junit.Test

class BoxDrawer {
  data class Size(val width: Int, val height: Int)
  data class Point(val x: Int, val y: Int)

  private fun getBoxPoints(childSizes: List<Size>, parentSize: Size): List<Point> {
    if (childSizes.isEmpty()) {
      return emptyList()
    }

    val list = mutableListOf<Point>()
    var columnWidth = childSizes[0].width

    val parentWidth = parentSize.width
    var availableWidth = parentWidth
    val parentHeight = parentSize.height
    var availableHeight = parentHeight

    for (box in childSizes) {
      val height = box.height
      val width = box.width

      if (box.height < 1 || box.width < 1) {
        return emptyList() // invalid box
      }

      if (height > availableHeight) {
        availableHeight = parentHeight
        availableWidth -= columnWidth
        columnWidth = 0
      }

      if (width > availableWidth || height > parentHeight) {
        return emptyList()
      }

      if (width > columnWidth) {
        columnWidth = width
      }

      val x = parentWidth - availableWidth
      val y = parentHeight - availableHeight
      availableHeight -= height

      list.add(Point(x, y))
    }

    return list
  }

  @Test
  internal fun `no boxes returns empty list`() {
    assertEquals(0, getBoxPoints(emptyList(), Size(-1, -1)).size)
  }

  @Test
  fun `invalid size returns empty list`() {
    assertEquals(
      emptyList<Point>(),
      getBoxPoints(listOf(Size(10, 10)), Size(-1, -1))
    )
  }

  @Test
  fun `invalid box returns empty list`() {
    assertEquals(
      emptyList<Point>(),
      getBoxPoints(listOf(Size(0, 0)), Size(1, 1))
    )
  }

  @Test
  fun `valid box produces valid list`() {
    val parentSize = Size(10, 10)
    val boxPoints = getBoxPoints(listOf(parentSize), parentSize)
    val expectedPoints = listOf(Point(0, 0))

    assertEquals(expectedPoints, boxPoints)
  }

  @Test
  fun `valid boxes produce valid list`() {
    val parentSize = Size(10, 10)
    val boxPoints = getBoxPoints(
      listOf(
        Size(5, 5),
        Size(4, 4),
        Size(5, 5),
        Size(5, 5),
      ), parentSize
    )
    val expectedPoints = listOf(Point(0, 0), Point(0, 5), Point(5, 0), Point(5, 5))

    assertEquals(expectedPoints, boxPoints)
  }
}
