package com.diegoflores.app_pmoviles.lab8.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination

fun NavGraphBuilder.loginScreen(
    onNavigateLogin: () -> Unit
){
    composable<LoginDestination> {
        LoginRoute (
            onNavigateLogin = onNavigateLogin
        )
    }
}