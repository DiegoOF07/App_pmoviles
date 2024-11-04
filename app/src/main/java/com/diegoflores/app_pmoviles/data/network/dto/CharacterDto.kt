package com.diegoflores.app_pmoviles.data.network.dto

import com.diegoflores.app_pmoviles.domain.model.Character
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String
)

fun CharacterDto.mapToCharacterModel(): Character{
    return Character(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        image = image,
    )
}