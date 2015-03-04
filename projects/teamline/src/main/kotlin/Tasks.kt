package org.trnqilo

import org.apache.poi.ss.usermodel.Row
import java.time.LocalDate
import kotlin.math.ceil
import kotlin.math.roundToInt

private const val TASK_SHEET = 0
private const val NAME = 0
private const val DESCRIPTION = 2
private const val PERSON = 3
private const val ESTIMATE = 5
private const val START = 6

data class Task(
  val personName: String,
  val name: String,
  val description: String,
  val start: LocalDate,
  val days: Int,
)

fun loadTasks(people: List<Person>): List<Task> = mutableListOf<Task>().apply {
  loadSheet(TASK_SHEET).eachRow { row ->
    processTaskRow(row, people) { task ->
      add(task)
    }
  }
  sortBy { it.start }
  sortBy { it.personName }
}

private fun processTaskRow(
  row: Row, people: List<Person>, onSuccess: (task: Task) -> Unit
) {
  cellString(row, PERSON)?.let { person ->
    cellString(row, NAME)?.let { name ->
      cellString(row, DESCRIPTION)?.let { description ->
        cellNumber(row, ESTIMATE)?.let { estimate ->
          cellDate(row, START)?.let { start ->
            onSuccess(
              Task(
                person,
                name,
                description,
                start.toLocalDate(),
                getTaskDays(people, person, estimate),
              )
            )
          }
        }
      }
    }
  }
}

private fun getTaskDays(people: List<Person>, personName: String, estimate: Double) =
  people.find { person -> person.name == personName }?.let { person ->
    ceil(estimate / person.hoursPerDay).roundToInt()
  } ?: 0
