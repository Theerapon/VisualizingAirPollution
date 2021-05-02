package com.gis.spatial.visualizingAirPollution.extensions

import com.gis.spatial.visualizingAirPollution.model.entities.AirPollutionPM25
import com.gis.spatial.visualizingAirPollution.model.entities.Position
import com.gis.spatial.visualizingAirPollution.model.responses.MarkerResponse

fun AirPollutionPM25.toMarker() = MarkerResponse (
    position = Position (
        latitude!!.toDouble(),
        longitude!!.toDouble()
    )
)