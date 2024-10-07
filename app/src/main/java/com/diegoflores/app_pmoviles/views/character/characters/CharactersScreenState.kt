package com.diegoflores.app_pmoviles.views.character.characters

import com.diegoflores.app_pmoviles.data.Character

data class CharactersScreenState(
    val isLoading: Boolean = false,
    val data: List<Character> = listOf(),
    val hasError: Boolean = false
)