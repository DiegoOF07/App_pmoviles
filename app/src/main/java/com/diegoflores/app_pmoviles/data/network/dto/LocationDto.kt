package com.diegoflores.app_pmoviles.data.network.dto

import com.diegoflores.app_pmoviles.domain.model.Location
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)

fun LocationDto.mapToLocationModel(): Location{
    return Location(
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}