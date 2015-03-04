package org.trnqilo

import java.time.LocalDate

fun buildTimeline(tasks: List<Task>): List<List<String>> =
  mutableListOf<List<String>>().apply {
    getHeaderDates(tasks).let { dates ->
      add(mutableListOf("").apply { addAll(dates.map { it.dayOfWeek.name }) })
      add(mutableListOf("").apply { addAll(dates.map { it.toString() }) })

      tasks.forEach { task ->
        var daysToCopy = -1
        add(mutableListOf(task.personName).apply {
          dates.forEach { date ->
            if (daysToCopy > 0) {
              --daysToCopy
              add(task.description)
            } else if (date == task.start) {
              add(task.description)
              daysToCopy = task.days.minus(1)
            } else {
              daysToCopy = -1
              add("")
            }
          }
        })
      }
    }
  }

private fun getHeaderDates(tasks: List<Task>): List<LocalDate> {
  val dates = tasks.map { it.start }.sorted()
  val startDate = dates.first()
  val endDate = dates.last().plusDays(90)

  return mutableListOf<LocalDate>().apply {
    var date = startDate
    while (date != endDate) {
      date.dayOfWeek.name.apply {
        if (this != "SATURDAY" && this != "SUNDAY") {
          add(date)
        }
      }
      date = date.plusDays(1)
    }
  }
}
