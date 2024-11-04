package com.diegoflores.app_pmoviles.views.character.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.toRoute
import com.diegoflores.app_pmoviles.data.CharacterDb
import com.diegoflores.app_pmoviles.data.repository.CharacterRepository
import com.diegoflores.app_pmoviles.di.Dependencies
import com.diegoflores.app_pmoviles.views.character.characters.CharactersViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel (
    savedStateHandle: SavedStateHandle,
    private val characterRepository: CharacterRepository
): ViewModel(){
    private var getDataJob: Job? = null
    private val character = savedStateHandle.toRoute<DetailsDestination>()
    private val _state = MutableStateFlow(DetailsScreenState())
    val state = _state.asStateFlow()

    init {
        getCharacterDetailsData()
    }

    fun getCharacterDetailsData(){
        getDataJob = viewModelScope.launch {
            _state.update { it.copy(
                hasError = false,
                isLoading = true
            ) }
            val characterDetails = characterRepository.getCharacterById(
                character.characterId
            )

            _state.update { it.copy(
                isLoading = false,
                data = characterDetails
            ) }
        }
    }

    fun simulateError(){
        getDataJob?.cancel()
        _state.update { it.copy(
            hasError = true,
            isLoading = false
        ) }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])
                val db = Dependencies.provideDatabase(application)
                val savedStateHandle = this.createSavedStateHandle()
                DetailsViewModel(
                    savedStateHandle = savedStateHandle,
                    characterRepository = CharacterRepository(db.CharacterDao())
                )
            }
        }
    }
}