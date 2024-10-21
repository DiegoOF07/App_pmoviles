package com.diegoflores.app_pmoviles.data.repository

import com.diegoflores.app_pmoviles.data.localDb.dao.LocationDAO
import com.diegoflores.app_pmoviles.data.localDb.entity.mapToEntity
import com.diegoflores.app_pmoviles.data.localDb.entity.mapToModel
import com.diegoflores.app_pmoviles.domain.model.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationRepository(
    private val locationDAO: LocationDAO
){
    suspend fun getAllLocations(): List<Location> {
        val locations = locationDAO.getAllLocations()
        return locations.map { it.mapToModel() }
    }

    suspend fun getLocationById(id: Int): Location {
        val location = locationDAO.getLocationById(id)
        return location.mapToModel()
    }

    suspend fun insertAll(locations: List<Location>){
        val locationEntities = locations.map{ it.mapToEntity()}
        locationDAO.insertAll(locationEntities)
    }
}