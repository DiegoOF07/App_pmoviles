package com.diegoflores.app_pmoviles.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationsListDto(
    val results: List<LocationDto>,
)