package com.diegoflores.app_pmoviles.views.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.diegoflores.app_pmoviles.views.character.details.DetailsDestination
import com.diegoflores.app_pmoviles.views.character.login.LoginRoute
import kotlinx.serialization.Serializable

@Serializable
data object ProfileDestination

fun NavController.toProfileScreen(
    destination: ProfileDestination,
    navOptions: NavOptions? = null
) {
    this.navigate(destination, navOptions)
}

fun NavGraphBuilder.profileScreen(
    onLogOut: () -> Unit
){
    composable<ProfileDestination> {
        ProfileRoute (
            onLogOut = onLogOut
        )
    }
}