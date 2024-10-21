package com.diegoflores.app_pmoviles.presentation.views.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.diegoflores.app_pmoviles.data.DataStoreUserPrefs
import com.diegoflores.app_pmoviles.dataStore
import com.diegoflores.app_pmoviles.domain.UserPreferences
import com.diegoflores.app_pmoviles.presentation.views.character.login.LoginViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userPreferences: UserPreferences
): ViewModel(){

    private val _state = MutableStateFlow(ProfileScreenState())
    val state = _state.asStateFlow()

    init {
        getUserLogged()
    }

    private fun getUserLogged() {
        viewModelScope.launch {
            _state.update { it.copy(
                name = userPreferences.getName()
            )}
        }
    }

    fun logOut(){
        viewModelScope.launch {
            userPreferences.setName("")
            userPreferences.logOut()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])
                ProfileViewModel(
                    userPreferences = DataStoreUserPrefs(application.dataStore)
                )
            }
        }
    }

}