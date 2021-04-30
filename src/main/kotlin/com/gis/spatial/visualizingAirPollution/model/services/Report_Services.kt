package com.gis.spatial.visualizingAirPollution.model.services

import com.gis.spatial.visualizingAirPollution.repositories.AirPollutionPM25_Repository
import org.springframework.stereotype.Service

@Service
class Report_Services(private val airpollutionpm25Repository: AirPollutionPM25_Repository) {

}