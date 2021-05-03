package com.gis.spatial.visualizingAirPollution.helper

import com.gis.spatial.visualizingAirPollution.model.entities.AirPollutionPM25
import com.gis.spatial.visualizingAirPollution.model.responses.AvgResponse
import com.gis.spatial.visualizingAirPollution.model.responses.CountryCityResponse
import com.gis.spatial.visualizingAirPollution.model.responses.HistoricalResponse
import com.gis.spatial.visualizingAirPollution.model.responses.PopulationResponse
import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
import com.vividsolutions.jts.geom.PrecisionModel
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList

@Component
class ExcelHelper {
    companion object {
        var TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        var SHEET = "WHO_AirQuality_Database_2018"

        fun hasExcelFormat(file: MultipartFile): Boolean {
            return TYPE == file.contentType
        }

        fun excelToAirPollutionPM25s(input: InputStream): ArrayList<AirPollutionPM25> {
            try {
                val workbook: Workbook = XSSFWorkbook(input)

                val sheet: Sheet = workbook.getSheet(SHEET)
                val rows: Iterator<Row> = sheet.iterator()

                val airPollutionPM25s: ArrayList<AirPollutionPM25> = arrayListOf()
                var rowNumber = 0
                while (rows.hasNext()) {
                    val currentRow = rows.next()

                    // skip header
                    if (rowNumber == 0) {
                        rowNumber++
                        continue
                    }

                    val cellsInRow: Iterator<Cell> = currentRow.iterator()
                    val airPollutionPM25 = AirPollutionPM25()
                    var cellIdx = 0
                    while (cellsInRow.hasNext()) {
                        val currentCell = cellsInRow.next()
                        when (cellIdx) {
                            0 -> airPollutionPM25.country = currentCell.stringCellValue
                            1 -> airPollutionPM25.city = currentCell.stringCellValue
                            2 -> airPollutionPM25.year = currentCell.numericCellValue.toInt().toString()
                            3 -> airPollutionPM25.pm25 = currentCell.numericCellValue
                            4 -> airPollutionPM25.latitude = currentCell.numericCellValue
                            5 -> airPollutionPM25.longitude = currentCell.numericCellValue
                            6 -> airPollutionPM25.population = currentCell.numericCellValue
                            7 -> airPollutionPM25.wbinc16_text = currentCell.stringCellValue
                            8 -> airPollutionPM25.region = currentCell.stringCellValue
                            9 -> airPollutionPM25.conc_pm25 = currentCell.stringCellValue
                            10 -> airPollutionPM25.color_pm25 = currentCell.stringCellValue
                            else -> {
                            }
                        }
                        cellIdx++
                    }
                    airPollutionPM25.id = currentRow.rowNum
                    //airPollutionPM25.geom = null
                    airPollutionPM25.geom = GeometryFactory(PrecisionModel(), 4326).createPoint(
                        Coordinate(
                            airPollutionPM25.longitude!!.toDouble(),
                            airPollutionPM25.latitude!!.toDouble()
                        )
                    )
                    airPollutionPM25s.add(airPollutionPM25)
                }
                workbook.close()

                return airPollutionPM25s
            } catch (e: IOException) {
                throw RuntimeException("fail to parse Excel file: " + e.message)
            }
        }

        fun countryPM25sToExcel(airPollutionPM25s: List<CountryCityResponse>): ByteArrayInputStream {
            try {
                XSSFWorkbook().use { workbook ->
                    ByteArrayOutputStream().use { out ->
                        val sheet = workbook.createSheet(SHEET)
                        var HEADERs =
                            arrayOf(
                                "country", "city"
                            )

                        // Header
                        val headerRow = sheet.createRow(0)
                        for (col in HEADERs.indices) {
                            val cell = headerRow.createCell(col)
                            cell.setCellValue(HEADERs[col])
                        }
                        var rowIdx = 1
                        for (airPollutionPM25 in airPollutionPM25s) {
                            val row = sheet.createRow(rowIdx++)
                            row.createCell(0).setCellValue(airPollutionPM25.country)
                            row.createCell(1).setCellValue(airPollutionPM25.city)
                        }
                        workbook.write(out)
                        return ByteArrayInputStream(out.toByteArray())
                    }
                }
            } catch (e: IOException) {
                throw RuntimeException("fail to import data to Excel file: " + e.message)
            }
        }

        fun avgResponseToExcel(avgResponses: List<AvgResponse>): ByteArrayInputStream {
            try {
                XSSFWorkbook().use { workbook ->
                    ByteArrayOutputStream().use { out ->
                        val sheet: Sheet = workbook.createSheet(SHEET)
                        var header =  arrayOf("country", "average")

                        // Header
                        val headerRow = sheet.createRow(0)
                        for (col in header.indices) {
                            val cell = headerRow.createCell(col)
                            cell.setCellValue(header[col])
                        }
                        var rowIdx = 1
                        for (avgResponse in avgResponses) {
                            val row = sheet.createRow(rowIdx++)
                            row.createCell(0).setCellValue(avgResponse.country)
                            row.createCell(1).setCellValue(avgResponse.avg)
                        }
                        workbook.write(out)
                        return ByteArrayInputStream(out.toByteArray())
                    }
                }
            } catch (e: IOException) {
                throw RuntimeException("fail to import data to Excel file: " + e.message)
            }
        }

        fun popResponseToExcel(popResponses: List<PopulationResponse>): ByteArrayInputStream {
            try {
                XSSFWorkbook().use { workbook ->
                    ByteArrayOutputStream().use { out ->
                        val sheet: Sheet = workbook.createSheet(SHEET)
                        var header =  arrayOf("year", "level", "population")

                        // Header
                        val headerRow = sheet.createRow(0)
                        for (col in header.indices) {
                            val cell = headerRow.createCell(col)
                            cell.setCellValue(header[col])
                        }
                        var rowIdx = 1
                        for (popResponse in popResponses) {
                            val row = sheet.createRow(rowIdx++)
                            row.createCell(0).setCellValue(popResponse.year)
                            row.createCell(1).setCellValue(popResponse.level)
                            row.createCell(2).setCellValue(popResponse.population)
                        }
                        workbook.write(out)
                        return ByteArrayInputStream(out.toByteArray())
                    }
                }
            } catch (e: IOException) {
                throw RuntimeException("fail to import data to Excel file: " + e.message)
            }
        }

        fun hisResponseToExcel(hisResponse: List<HistoricalResponse>): ByteArrayInputStream {
            try {
                XSSFWorkbook().use { workbook ->
                    ByteArrayOutputStream().use { out ->
                        val sheet: Sheet = workbook.createSheet(SHEET)
                        var header =  arrayOf("country", "year", "pm25")

                        // Header
                        val headerRow = sheet.createRow(0)
                        for (col in header.indices) {
                            val cell = headerRow.createCell(col)
                            cell.setCellValue(header[col])
                        }
                        var rowIdx = 1
                        for (rs in hisResponse) {
                            val row = sheet.createRow(rowIdx++)
                            row.createCell(0).setCellValue(rs.country)
                            row.createCell(1).setCellValue(rs.year)
                            row.createCell(2).setCellValue(rs.pm)
                        }
                        workbook.write(out)
                        return ByteArrayInputStream(out.toByteArray())
                    }
                }
            } catch (e: IOException) {
                throw RuntimeException("fail to import data to Excel file: " + e.message)
            }
        }

    }
}
