package com.diegoflores.app_pmoviles.data.localDb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.diegoflores.app_pmoviles.domain.model.Character

@Entity
data class CharacterEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
)

fun CharacterEntity.mapToModel(): Character {
    return Character(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        image = image
    )
}

fun Character.mapToEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        image = image
    )
}