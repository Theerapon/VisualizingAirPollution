package com.gis.spatial.visualizingAirPollution.model.services

import com.gis.spatial.visualizingAirPollution.repositories.AirPollutionPM25_Repository
import com.gis.spatial.visualizingAirPollution.repositories.World_Repository
import org.springframework.stereotype.Service

@Service
class MapVisualizing_Services(
    private val airpollutionpm25Repository: AirPollutionPM25_Repository,
    private val worldRepository: World_Repository
) {

}