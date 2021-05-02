package com.gis.spatial.visualizingAirPollution.model.services

import com.gis.spatial.visualizingAirPollution.repositories.AirPollutionPM25Repository
import com.gis.spatial.visualizingAirPollution.repositories.WorldRepository
import org.springframework.stereotype.Service

@Service
class DropDownServices(
    private val airpollutionpm25Repository: AirPollutionPM25Repository
) {
    // DISTINCT For Year Drop Down
    fun getDistinctYear(): List<String> {
        return airpollutionpm25Repository.distinctYear()
    }

    // DISTINCT For Country Drop Down
    fun getDistinctCountry(): List<String> {
        return airpollutionpm25Repository.distinctCountry()
    }

    // DISTINCT For Level Color PM 2.5 Drop Down
    fun getDistinctColor(): List<String> {
        return airpollutionpm25Repository.distinctColor()
    }

}