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
        val split =  airpollutionpm25Repository.distinctYear()
        for (str in split){
            str.split(" ")[0]
        }
        return split
    }

    // DISTINCT For Country Drop Down
    fun getDistinctCountry(): List<String> {
        val split =  airpollutionpm25Repository.distinctCountry()
        for (str in split){
            str.split(" ")[0]
        }
        return split
    }

    // DISTINCT For Level Color PM 2.5 Drop Down
    fun getDistinctColor(): List<String> {
        val split =  airpollutionpm25Repository.distinctColor()
        for (str in split){
            str.split(" ")[0]
        }
        return split
    }

}