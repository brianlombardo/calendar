package org.trnqilo

import org.apache.poi.ss.usermodel.Row

private const val PEOPLE_SHEET = 2
private const val NAME = 0
private const val HOURS = 12

data class Person(
  val name: String,
  val hoursPerDay: Double
)

fun loadPeople(): List<Person> = mutableListOf<Person>().apply {
  loadSheet(PEOPLE_SHEET).eachRow { row ->
    processPersonRow(row) { person ->
      add(person)
    }
  }
}

private fun processPersonRow(
  row: Row, onSuccess: (person: Person) -> Unit
) {
  cellString(row, NAME)?.let { person ->
    cellNumber(row, HOURS)?.let { hours ->
      onSuccess(Person(person, hours))
    }
  }
}
