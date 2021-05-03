package com.gis.spatial.visualizingAirPollution.controller

import com.gis.spatial.visualizingAirPollution.model.entities.Position
import com.gis.spatial.visualizingAirPollution.model.responses.MarkerResponse
import com.gis.spatial.visualizingAirPollution.model.services.DropDownServices
import com.gis.spatial.visualizingAirPollution.model.services.MapVisualizingServices
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/visualizing")
class VisualizingController (
    private val visualizingServices: MapVisualizingServices,
    private val dropdownServices: DropDownServices
) {

    @GetMapping("/findCity")
    fun getCityPoint(
        @RequestParam year: String
    ): List<MarkerResponse> {
        return visualizingServices.findCityPositionByYear(year)
    }

    @GetMapping("/findClosetCity")
    fun getClosetCityPosition(
        @RequestParam city: String
    ): List<MarkerResponse> {
        return visualizingServices.findClosetCityPosition(city)
    }

    @GetMapping("/findThailandNeighbor")
    fun getThailandNeighborPositionByYear(
        @RequestParam year: String
    ): List<MarkerResponse> {
        return visualizingServices.findThailandNeighborPositionByYear(year)
    }

    @GetMapping("/findMBR")
    fun getMBRPositionByYear(
        @RequestParam year: String
    ): List<Position> {
        return visualizingServices.findMBRPositionByYear(year)
    }

    @GetMapping("/findHighestCity")
    fun getAllHighestCityPointsPositionByYear(
        @RequestParam year: String
    ): List<MarkerResponse> {
        return visualizingServices.findAllHighestCityPointsPositionByYear(year)
    }

    @GetMapping("/findCityLowIncome")
    fun getCitiesHaveLowIncome(
        @RequestParam year: String
    ): List<MarkerResponse> {
        return visualizingServices.findCitiesHaveLowIncome(year)
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


}