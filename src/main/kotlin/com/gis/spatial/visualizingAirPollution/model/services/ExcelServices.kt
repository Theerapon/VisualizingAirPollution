package com.gis.spatial.visualizingAirPollution.model.services

import com.gis.spatial.visualizingAirPollution.helper.ExcelHelper
import com.gis.spatial.visualizingAirPollution.model.entities.AirPollutionPM25
import com.gis.spatial.visualizingAirPollution.model.responses.AvgResponse
import com.gis.spatial.visualizingAirPollution.model.responses.CountryCityResponse
import com.gis.spatial.visualizingAirPollution.model.responses.HistoricalResponse
import com.gis.spatial.visualizingAirPollution.model.responses.PopulationResponse
import com.gis.spatial.visualizingAirPollution.repositories.AirPollutionPM25Repository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.IOException
import java.util.ArrayList

@Service
class ExcelServices(
    private val airPollutionPM25Repository: AirPollutionPM25Repository
) {
    fun save(file: MultipartFile) {
        try {
            val airPollutionPM25s: ArrayList<AirPollutionPM25> = ExcelHelper.excelToAirPollutionPM25s(file.inputStream)
            airPollutionPM25Repository.saveAll(airPollutionPM25s)
        } catch (e: IOException) {
            throw RuntimeException("fail to store excel data: " + e.message)
        }
    }

    fun loadListCountryByPM25(pm25: Double, year: String): ByteArrayInputStream {
        val airPollutionPM25: List<CountryCityResponse> = airPollutionPM25Repository.findCountryAndCityByPm25InYear(pm25, year)
        return ExcelHelper.countryPM25sToExcel(airPollutionPM25)
    }

    fun loadAVG(): ByteArrayInputStream {
        val avgResponses: List<AvgResponse> = airPollutionPM25Repository.findAVGGroupByCountry()
        return ExcelHelper.avgResponseToExcel(avgResponses)
    }

    fun loadHisByCountry(country: String): ByteArrayInputStream {
        val airPollutionPM25: List<HistoricalResponse> = airPollutionPM25Repository.findHistoricalByCountry(country)
        return ExcelHelper.hisResponseToExcel(airPollutionPM25)
    }

    fun loadPop(year: String, color: String): ByteArrayInputStream {
        val popResponses: List<PopulationResponse> = airPollutionPM25Repository.findTotalPopulationByYearAndColor(year, color)
        return ExcelHelper.popResponseToExcel(popResponses)
    }
}