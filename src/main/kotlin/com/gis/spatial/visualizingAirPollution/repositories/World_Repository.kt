package com.gis.spatial.visualizingAirPollution.repositories

import org.aspectj.weaver.World
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface World_Repository : JpaRepository<World, String> {
}