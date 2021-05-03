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



    }
}
