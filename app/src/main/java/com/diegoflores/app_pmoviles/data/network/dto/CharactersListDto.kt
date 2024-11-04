package com.diegoflores.app_pmoviles.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CharactersListDto(
    val results: List<CharacterDto>,
)