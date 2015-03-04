#!/usr/bin/env groovy

@Grab(group = 'org.apache.poi', module = 'poi-ooxml', version = '5.2.3')

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import static java.lang.System.getenv
import static java.net.URI.create
import static java.net.http.HttpClient.newHttpClient
import static java.net.http.HttpRequest.newBuilder
import static java.net.http.HttpResponse.BodyHandlers.ofString

def personColumnName = 'person'
def estimateColumnName = 'estimate'
def startColumnName = 'start'
def workbook = new XSSFWorkbook(new File('./example.xlsx'))
def sheet = workbook.getSheetAt 0
def headers = sheet.getRow 0

headers.cellIterator().each { cell ->
  switch (cell.stringCellValue) {
    case personColumnName -> personColumnIndex = cell.columnIndex
    case estimateColumnName -> estimateColumnIndex = cell.columnIndex
    case startColumnName -> startColumnIndex = cell.columnIndex
  }
}

def getRowData(Row row) {
  [row.getCell(personColumnIndex), row.getCell(estimateColumnIndex), row.getCell(startColumnIndex)]
}

def callApi(row) {
  def (person, estimate, start) = getRowData row
  println "${person} ${estimate} ${start}"

  newHttpClient().tap {
    def request = newBuilder()
        .uri(create("${getenv('SERVER_URL')}/${person}"))
        .header("User-Agent", "Java-HttpClient")
        .GET()
        .build()
    try {
      send(request, ofString()).tap {
        println "$person: ${statusCode()} ${body()}"
      }
    } catch (Exception e) {
      println "$person: ${e.message}"
    }
  }
}

sheet.each { if (it.rowNum) callApi it }

workbook.close()
