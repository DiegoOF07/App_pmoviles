package com.diegoflores.app_pmoviles.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CharacterProfileDto(
    val data: CharacterDto,
    val message: String,
    val status: Int
)