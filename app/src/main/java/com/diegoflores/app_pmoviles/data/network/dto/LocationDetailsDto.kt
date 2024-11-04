package com.diegoflores.app_pmoviles.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationDetailsDto(
    val data: LocationDto,
    val message: String,
    val status: Int
)