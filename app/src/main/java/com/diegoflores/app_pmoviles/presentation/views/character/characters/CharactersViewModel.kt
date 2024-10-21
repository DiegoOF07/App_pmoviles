package com.diegoflores.app_pmoviles.views.character.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.diegoflores.app_pmoviles.data.CharacterDb
import com.diegoflores.app_pmoviles.data.DataStoreUserPrefs
import com.diegoflores.app_pmoviles.data.repository.CharacterRepository
import com.diegoflores.app_pmoviles.dataStore
import com.diegoflores.app_pmoviles.di.Dependencies
import com.diegoflores.app_pmoviles.presentation.views.profile.ProfileViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharactersViewModel(private val characterRepository: CharacterRepository): ViewModel() {
    private val characterDb = CharacterDb()
    private val _state = MutableStateFlow(CharactersScreenState())
    val state = _state.asStateFlow()

    init {
        loadData()
        getCharactersData()
    }

    private fun loadData(){
        if(_state.value.data.isEmpty()){
            viewModelScope.launch {
                characterRepository.insertAll(characterDb.getAllCharacters())
            }
        }
    }

     fun getCharactersData(){
      viewModelScope.launch {
          _state.update { it.copy(
              hasError = false,
              isLoading = true
          ) }

          delay(4000L)
          val characters = characterRepository.getAllCharacters()

          _state.update { it.copy(
              isLoading = false,
              data = characters
          ) }
      }
    }

    fun simulateError(){
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
                CharactersViewModel(
                    characterRepository = CharacterRepository(db.CharacterDao())
                )
            }
        }
    }
}