package com.diegoflores.app_pmoviles.presentation.navigation

sealed interface AuthStatus {
    data object Loading: AuthStatus
    data object Authenticated: AuthStatus
    data object NonAuthenticated: AuthStatus
}