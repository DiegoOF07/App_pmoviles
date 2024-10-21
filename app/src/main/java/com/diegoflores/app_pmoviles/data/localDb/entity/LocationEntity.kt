package com.diegoflores.app_pmoviles.data.localDb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.diegoflores.app_pmoviles.domain.model.Location

@Entity
data class LocationEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: String,
    val dimension: String
)

fun LocationEntity.mapToModel(): Location {
    return Location(
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}

fun Location.mapToEntity(): LocationEntity {
    return LocationEntity(
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}