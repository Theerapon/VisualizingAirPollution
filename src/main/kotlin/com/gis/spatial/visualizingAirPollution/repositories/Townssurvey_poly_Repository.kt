package com.gis.spatial.visualizingAirPollution.repositories

import com.gis.spatial.visualizingAirPollution.model.entities.Townssurvey_Poly
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface Townssurvey_poly_Repository : JpaRepository<Townssurvey_Poly, String> {

}