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
        val distinct: MutableList<String> = mutableListOf()
        for (str in split){
            distinct.add(str.split(" ")[0])
        }
        return distinct
    }

    // DISTINCT For Country Drop Down
    fun getDistinctCountry(): List<String> {
        val split =  airpollutionpm25Repository.distinctCountry()
        val distinct: MutableList<String> = mutableListOf()
        for (str in split){
            distinct.add(str.split(" ")[0])
        }
        return distinct
    }

    // DISTINCT For Level Color PM 2.5 Drop Down
    fun getDistinctColor(): List<String> {
        val split =  airpollutionpm25Repository.distinctColor()
        val distinct: MutableList<String> = mutableListOf()
        for (str in split){
            distinct.add(str.split(" ")[0])
        }
        return distinct
    }

}