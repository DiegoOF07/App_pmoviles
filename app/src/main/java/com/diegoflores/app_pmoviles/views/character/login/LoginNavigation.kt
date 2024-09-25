package com.diegoflores.app_pmoviles.views.character.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.diegoflores.app_pmoviles.views.character.details.DetailsDestination
import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination

fun NavController.toLogin(
    destination: LoginDestination,
    navOptions: NavOptions? = null
){
    this.navigate(destination, navOptions)
}

fun NavGraphBuilder.loginScreen(
    onNavigateLogin: () -> Unit
){
    composable<LoginDestination> {
        LoginRoute (
            onNavigateLogin = onNavigateLogin
        )
    }
}