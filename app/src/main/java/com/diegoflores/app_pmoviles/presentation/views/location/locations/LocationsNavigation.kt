package com.diegoflores.app_pmoviles.views.location.locations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.diegoflores.app_pmoviles.views.character.details.DetailsDestination
import com.diegoflores.app_pmoviles.views.character.login.LoginRoute
import com.diegoflores.app_pmoviles.views.profile.ProfileRoute
import kotlinx.serialization.Serializable

@Serializable
data object LocationsDestination

fun NavController.toLocationsScreen(
    destination: LocationsDestination,
    navOptions: NavOptions? = null
) {
    this.navigate(destination, navOptions)
}

fun NavGraphBuilder.locationsScreen(
    onNavigateLocation: (Int) -> Unit
){
    composable<LocationsDestination> {
        LocationsRoute (
            onNavigateLocation = onNavigateLocation
        )
    }
}