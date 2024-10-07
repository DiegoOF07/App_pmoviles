package com.diegoflores.app_pmoviles.views.character.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.diegoflores.app_pmoviles.data.CharacterDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel (
    savedStateHandle: SavedStateHandle
): ViewModel(){
    private val characterDb = CharacterDb()
    private val character = savedStateHandle.toRoute<DetailsDestination>()
    private val _state = MutableStateFlow(DetailsScreenState())
    val state = _state.asStateFlow()

    init {
        getCharacterDetailsData()
    }

    fun getCharacterDetailsData(){
        viewModelScope.launch {
            _state.update { it.copy(
                hasError = false,
                isLoading = true
            ) }

            delay(2000L)
            val characterDetails = characterDb.getCharacterById(
                character.characterId
            )

            _state.update { it.copy(
                isLoading = false,
                data = characterDetails
            ) }
        }
    }

    fun simulateError(){
        _state.update { it.copy(
            hasError = true,
            isLoading = false
        ) }
    }
}