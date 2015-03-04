package org.trnqilo

fun main() {
  saveSheetToWorkbook(
    buildTimeline(
      loadTasks(
        loadPeople()
      )
    )
  )
}
