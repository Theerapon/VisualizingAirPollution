package com.gis.spatial.visualizingAirPollution.repositories

import com.gis.spatial.visualizingAirPollution.model.entities.World
import com.vividsolutions.jts.geom.Geometry
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Repository
interface WorldRepository : JpaRepository<World, Int> {

    @Query(value = "SELECT w FROM World w WHERE w.name = ?1")
    fun findCountry(@Param("country") country: String): World

    @Query(value = "select * from world where world.geom.MakeValid().STTouches(?1) = 1", nativeQuery = true)
    fun findListNeighbor(@Param("country_geom") geom: Geometry): MutableList<World>

}