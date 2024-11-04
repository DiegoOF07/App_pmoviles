package com.diegoflores.app_pmoviles.views.character.characters

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.diegoflores.app_pmoviles.data.CharacterDb
import com.diegoflores.app_pmoviles.data.DataStoreUserPrefs
import com.diegoflores.app_pmoviles.data.network.KtorRickAndMortyApi
import com.diegoflores.app_pmoviles.data.network.dto.mapToCharacterModel
import com.diegoflores.app_pmoviles.data.repository.CharacterRepository
import com.diegoflores.app_pmoviles.dataStore
import com.diegoflores.app_pmoviles.di.Dependencies
import com.diegoflores.app_pmoviles.domain.network.RickAndMortyApi
import com.diegoflores.app_pmoviles.domain.network.util.map
import com.diegoflores.app_pmoviles.domain.network.util.onError
import com.diegoflores.app_pmoviles.domain.network.util.onSuccess
import com.diegoflores.app_pmoviles.presentation.views.profile.ProfileViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val characterRepository: CharacterRepository,
    private val rickAndMortyApi: RickAndMortyApi): ViewModel() {
    private val characterDb = CharacterDb()
    private var getDataJob: Job? = null
    private val _state = MutableStateFlow(CharactersScreenState())
    val state = _state.asStateFlow()

    init {
        loadCharactersFromApi()
        getCharactersFromRoom()
    }

    fun loadCharactersFromLocalDb(){
        viewModelScope.launch {
            characterRepository.insertAll(characterDb.getAllCharacters())
        }
    }

    fun loadCharactersFromApi(){
        viewModelScope.launch {
            if(characterRepository.getAllCharacters().isEmpty()) {
                rickAndMortyApi
                    .getAllCharacters()
                    .map { response ->
                        _state.update {
                            it.copy(
                                isLoading = true,
                                hasError = false,
                                statusMessage = null
                            )
                        }
                        response.results.map { it.mapToCharacterModel() }
                    }
                    .onSuccess { characters ->
                        characterRepository.insertAll(characters)
                        _state.update {
                            it.copy(
                                statusMessage = "Datos cargados de Internet",
                                isLoading = false,
                                hasError = false
                            )
                        }
                    }
                    .onError { error ->
                        _state.update {
                            it.copy(
                                statusMessage = "Los datos no se han cargado de Internet",
                                hasError = true,
                                isLoading = false
                            )
                        }
                        println(error)
                    }
            }
        }
    }

    fun getCharactersFromRoom(){
      getDataJob = viewModelScope.launch {
          _state.update { it.copy(
              hasError = false,
              isLoading = true
          ) }
          val characters = characterRepository.getAllCharacters()
          _state.update { it.copy(
              isLoading = false,
              data = characters
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
                val api = KtorRickAndMortyApi(Dependencies.provideHttpClient())
                CharactersViewModel(
                    characterRepository = CharacterRepository(db.CharacterDao()),
                    rickAndMortyApi = api
                )
            }
        }
    }
}