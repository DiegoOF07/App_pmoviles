package com.diegoflores.app_pmoviles.views.character.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegoflores.app_pmoviles.data.CharacterDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharactersViewModel: ViewModel() {
    private val characterDb = CharacterDb()
    private val _state = MutableStateFlow(CharactersScreenState())
    val state = _state.asStateFlow()

    init {
        getCharactersData()
    }

     fun getCharactersData(){
      viewModelScope.launch {
          _state.update { it.copy(
              hasError = false,
              isLoading = true
          ) }

          delay(4000L)
          val characters = characterDb.getAllCharacters()

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
}