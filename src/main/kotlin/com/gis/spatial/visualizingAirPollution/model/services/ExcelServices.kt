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
    
}