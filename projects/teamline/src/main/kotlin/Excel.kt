package org.trnqilo

import org.apache.poi.ss.usermodel.FillPatternType.NO_FILL
import org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND
import org.apache.poi.ss.usermodel.IndexedColors.GREY_25_PERCENT
import org.apache.poi.ss.usermodel.IndexedColors.LIGHT_GREEN
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory.create
import org.apache.poi.ss.util.CellRangeAddress
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.LocalDateTime


const val SPREADSHEET = "./example_input.xlsx"
const val SPREADSHEET_SAVED = "./example_output.xlsx"

fun loadWorkbook(): Workbook =
  FileInputStream(SPREADSHEET).let { stream ->
    create(stream).apply {
      stream.close()
    }
  }

fun saveSheetToWorkbook(rows: List<List<String>>, workbook: Workbook = loadWorkbook()) =
  FileOutputStream(SPREADSHEET_SAVED).let { stream ->
    workbook.apply {
      removeSheetAt(1)
      "Timeline".let {
        buildSheet(it, rows)
        setSheetOrder(it, 1)
      }
      write(stream)
      stream.close()
    }
  }

private fun Workbook.buildSheet(name: String, rowItems: List<List<String>>) {
  createSheet(name).apply {
    rowItems.forEachIndexed { i, rowItem ->
      createRow(i).apply {
        rowItem.forEachIndexed { j, value ->
          createCell(j).apply {
            setCellValue(value)
            cellStyle = createCellStyle().apply {
              fillPattern = SOLID_FOREGROUND
              if (i < 2 || j == 0) {
                fillForegroundColor = GREY_25_PERCENT.getIndex()
              } else if (value.isNotBlank()) {
                fillForegroundColor = LIGHT_GREEN.getIndex()
              } else {
                fillPattern = NO_FILL
              }
            }
          }
        }
      }
    }
    mergeCells(rowItems)
    autoSizeColumns(rowItems)
  }
}

private fun Sheet.autoSizeColumns(rowItems: List<List<String>>) {
  for (i in rowItems[0].indices) {
    autoSizeColumn(i)
  }
}

private fun Sheet.mergeCells(rowItems: List<List<String>>) {
  for (i in 1 until rowItems.size) {
    getRow(i).apply {
      var start: Int? = null
      for (j in 1 until rowItems[0].size) {
        getCell(j)?.stringCellValue?.apply {
          if (isBlank() && start != null) {
            addMergedRegion(CellRangeAddress(i, i, start ?: 0, j - 1))
            start = null
          } else if (isNotBlank() && start == null) {
            start = j
          }
        }
      }
    }
  }
}

fun loadSheet(sheet: Int): Sheet =
  loadWorkbook().getSheetAt(sheet)

fun cellString(row: Row, cell: Int): String? = try {
  row.getCell(cell)?.stringCellValue
} catch (e: Exception) {
  null
}

fun cellDate(row: Row, cell: Int): LocalDateTime? = try {
  row.getCell(cell)?.localDateTimeCellValue
} catch (e: Exception) {
  null
}

fun cellNumber(row: Row, cell: Int): Double? = try {
  row.getCell(cell)?.numericCellValue
} catch (e: Exception) {
  null
}

fun Sheet.eachRow(onRow: (row: Row) -> Unit) =
  apply {
    var rowIndex = 0
    var empties = 0
    while (empties < 10) {
      getRow(++rowIndex)?.apply {
        empties = 0
        onRow(this)
      } ?: ++empties
    }
  }
