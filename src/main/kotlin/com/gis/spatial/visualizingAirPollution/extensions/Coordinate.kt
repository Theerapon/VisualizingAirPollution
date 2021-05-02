package com.gis.spatial.visualizingAirPollution.extensions

import com.gis.spatial.visualizingAirPollution.model.entities.Position
import com.vividsolutions.jts.geom.Coordinate

fun Coordinate.toFormat() = Position (
    lat = y,
    lng = x
)