package com.diegoflores.app_pmoviles.views.location.location_detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.diegoflores.app_pmoviles.views.character.details.DetailsRoute
import kotlinx.serialization.Serializable

@Serializable
data class LocationDetailsDestination(
    val locationId : Int
)

fun NavController.toLocationDetailsScreen(
    destination: LocationDetailsDestination,
    navOptions: NavOptions? = null
) {
    this.navigate(destination, navOptions)
}

fun NavGraphBuilder.locationDetailsScreen(
    onNavigateBack: () -> Unit
) {
    composable<LocationDetailsDestination> {
        LocationDetailsRoute(
            onNavigateBack = onNavigateBack
        )
    }
}