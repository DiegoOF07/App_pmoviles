package com.diegoflores.app_pmoviles.views.character.details

import com.diegoflores.app_pmoviles.domain.model.Character

data class DetailsScreenState(
    val isLoading: Boolean = false,
    val data: Character? = null,
    val hasError: Boolean = false
)