package com.gis.spatial.visualizingAirPollution.repositories

import com.gis.spatial.visualizingAirPollution.model.entities.TownssurveyPoly
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Repository
interface TownssurveyPolyRepository : JpaRepository<TownssurveyPoly, Int> {

}