package com.diegoflores.app_pmoviles.views.location.locations

import com.diegoflores.app_pmoviles.domain.model.Location

data class LocationsScreenState(
    val isLoading: Boolean = false,
    val data: List<Location> = listOf(),
    val hasError: Boolean = false
)