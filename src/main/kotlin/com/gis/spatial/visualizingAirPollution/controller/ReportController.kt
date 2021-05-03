package com.gis.spatial.visualizingAirPollution.controller

import com.gis.spatial.visualizingAirPollution.helper.ExcelHelper
import com.gis.spatial.visualizingAirPollution.model.responses.*
import com.gis.spatial.visualizingAirPollution.model.services.DropDownServices
import com.gis.spatial.visualizingAirPollution.model.services.ExcelServices
import com.gis.spatial.visualizingAirPollution.model.services.ReportServices
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.data.repository.query.Param
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/report")
class ReportController (
    private val excelServices: ExcelServices,
    private val reportServices: ReportServices,
    private val dropdownServices: DropDownServices
) {

    @GetMapping("/findByPM25")
    fun getCountryAndCityByPm25InYear(
        @RequestParam value: String,
        @RequestParam year: String
    ): ResponseEntity<List<CountryCityResponse>> {
        return try {
            val airPollutionPM25: List<CountryCityResponse> = reportServices.getListOfCountryAndCityByPm25InYear(value.toDouble(), year)
            if (airPollutionPM25.isEmpty())
                ResponseEntity<List<CountryCityResponse>>(HttpStatus.NO_CONTENT)
            else ResponseEntity<List<CountryCityResponse>>(airPollutionPM25, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity<List<CountryCityResponse>>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/findAvg")
    fun getListAvg(
       @RequestParam country: String
    ): ResponseEntity<List<AvgResponse>> {
        return try {
            val airPollutionResponse25: List<AvgResponse>
            if(!country.isEmpty()){
                airPollutionResponse25 = reportServices.getListAvgByCountry(country)
            } else {
                airPollutionResponse25 = reportServices.getListAvg()
            }

            if (airPollutionResponse25.isEmpty())
                ResponseEntity<List<AvgResponse>>(HttpStatus.NO_CONTENT)
            else ResponseEntity<List<AvgResponse>>(airPollutionResponse25, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity<List<AvgResponse>>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @GetMapping("/findHistoricalByCountry")
    fun getHistoricalByCountry(
        @RequestParam country: String
    ): ResponseEntity<List<HistoricalResponse>> {
        return try {
            val airPollutionPM25: List<HistoricalResponse> = reportServices.getListHistoricalpm25ByCountry(country)
            if (airPollutionPM25.isEmpty())
                ResponseEntity<List<HistoricalResponse>>(HttpStatus.NO_CONTENT)
            else ResponseEntity<List<HistoricalResponse>>(airPollutionPM25, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity<List<HistoricalResponse>>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/findTotalPopulation")
    fun getTotalPopulation(
        @RequestParam year: String,
        @RequestParam color: String
    ): ResponseEntity<List<PopulationResponse>> {
        return try {
            val airPollutionPM25: List<PopulationResponse> = reportServices.getListTotalPopulation(year, color)
            if (airPollutionPM25.isEmpty())
                ResponseEntity<List<PopulationResponse>>(HttpStatus.NO_CONTENT)
            else ResponseEntity<List<PopulationResponse>>(airPollutionPM25, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity<List<PopulationResponse>>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/distinctYear")
    fun getDistinctYear(): ResponseEntity<List<String>> {
        return try {
            val yearList: List<String> = dropdownServices.getDistinctYear()
            if (yearList.isEmpty())
                ResponseEntity<List<String>>(HttpStatus.NO_CONTENT)
            else ResponseEntity<List<String>>(yearList, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity<List<String>>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/distinctColor")
    fun getDistinctColor(): ResponseEntity<List<String>> {
        return try {
            val colorList: List<String> = dropdownServices.getDistinctColor()
            if (colorList.isEmpty())
                ResponseEntity<List<String>>(HttpStatus.NO_CONTENT)
            else ResponseEntity<List<String>>(colorList, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity<List<String>>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/distinctCountry")
    fun getDistinctCountry(): ResponseEntity<List<String>> {
        return try {
            val countryList: List<String> = dropdownServices.getDistinctCountry()
            if (countryList.isEmpty())
                ResponseEntity<List<String>>(HttpStatus.NO_CONTENT)
            else ResponseEntity<List<String>>(countryList, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity<List<String>>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/upload")
    fun uploadFile(
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<ResponseMessage> {
        var message = ""
        if (ExcelHelper.hasExcelFormat(file)) {
            return try {
                excelServices.save(file)
                message = "Uploaded the file successfully: " + file.originalFilename
                ResponseEntity.status(HttpStatus.CREATED).body(ResponseMessage(message))
            } catch (e: Exception) {
                message = "Could not upload the file: " + file.originalFilename + "!"
                ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(ResponseMessage(message + " clause:" + e.message.toString()))
            }
        }
        message = "Please upload an excel file!"
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessage(message))
    }

    @GetMapping("/loadFileCountryCity")
    fun getFileCountryCityByPM (
        @RequestParam value: String,
        @RequestParam year: String
    ): ResponseEntity<Resource> {
        val filename = "ListOfCountry.xlsx"
        val file = InputStreamResource(excelServices.loadListCountryByPM25(value.toDouble(), year))
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$filename")
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel;charset=utf-8"))
            .body(file)
    }

    @GetMapping("/loadFileAVG")
    fun getFileAVG (): ResponseEntity<Resource> {
        val filename = "ListOfAVG.xlsx"
        val file = InputStreamResource(excelServices.loadAVG())
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$filename")
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel;charset=utf-8"))
            .body(file)
    }

    @GetMapping("/loadFileHistorical")
    fun getFileHistorical (
        @RequestParam country: String
    ): ResponseEntity<Resource> {
        val filename = "ListOfHistorical.xlsx"
        val file = InputStreamResource(excelServices.loadHisByCountry(country))
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$filename")
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel;charset=utf-8"))
            .body(file)
    }

    @GetMapping("/loadFilePopulation")
    fun getFilePopulation (
        @RequestParam color: String,
        @RequestParam year: String
    ): ResponseEntity<Resource> {
        val filename = "ListOfPopulation.xlsx"
        val file = InputStreamResource(excelServices.loadPop(year, color))
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$filename")
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel;charset=utf-8"))
            .body(file)
    }
}