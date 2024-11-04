package com.diegoflores.app_pmoviles.views.character.characters

import com.diegoflores.app_pmoviles.domain.model.Character

data class CharactersScreenState(
    val isLoading: Boolean = false,
    val data: List<Character> = listOf(),
    val hasError: Boolean = false,
    val statusMessage: String? = null
)