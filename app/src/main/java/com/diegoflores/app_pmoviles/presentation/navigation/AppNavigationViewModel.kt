package com.diegoflores.app_pmoviles.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.diegoflores.app_pmoviles.data.DataStoreUserPrefs
import com.diegoflores.app_pmoviles.dataStore
import com.diegoflores.app_pmoviles.domain.UserPreferences
import com.diegoflores.app_pmoviles.presentation.views.character.login.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class AppNavigationViewModel(private val userPreferences: UserPreferences): ViewModel() {

    val authStatus = userPreferences.authStatus()
        .map { isLogged ->
            if (isLogged) {
                AuthStatus.Authenticated
            } else {
                AuthStatus.NonAuthenticated
            }
        }
        .catch { error ->
            println(error)
            AuthStatus.NonAuthenticated
        }
        .onEach { item -> println(item) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            AuthStatus.Loading
        )


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])
                AppNavigationViewModel(
                    userPreferences = DataStoreUserPrefs(application.dataStore)
                )
            }
        }
    }
    
}