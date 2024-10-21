package com.diegoflores.app_pmoviles.presentation.views.character.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.diegoflores.app_pmoviles.data.DataStoreUserPrefs
import com.diegoflores.app_pmoviles.dataStore
import com.diegoflores.app_pmoviles.domain.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userPreferences: UserPreferences
): ViewModel(){

    private val _state = MutableStateFlow(LoginScreenState())
    val state = _state.asStateFlow()

    fun onEvent(event: LoginScreenEvent){
        when(event){
            is LoginScreenEvent.onNameChange -> onNameChange(event.name)
            LoginScreenEvent.onSaveName -> onSaveName()
        }

    }

    private fun onNameChange(name: String){
        _state.update { it.copy(
            name = name
        )}
        if(_state.value.name == ""){
            _state.update { it.copy(
                isError = true
            ) }
        }else{
            _state.update { it.copy(
                isError = false
            ) }
        }
    }

    private fun onSaveName(){
        viewModelScope.launch {
            userPreferences.setName(_state.value.name)
            userPreferences.logIn()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])
                LoginViewModel(
                    userPreferences = DataStoreUserPrefs(application.dataStore)
                )
            }
        }
    }
}