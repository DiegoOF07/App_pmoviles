package com.diegoflores.app_pmoviles.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.navigation
import androidx.navigation.navOptions
import com.diegoflores.app_pmoviles.views.location.location_detail.LocationDetailsDestination
import com.diegoflores.app_pmoviles.views.location.location_detail.locationDetailsScreen
import com.diegoflores.app_pmoviles.views.location.location_detail.toLocationDetailsScreen
import com.diegoflores.app_pmoviles.views.location.locations.LocationsDestination
import com.diegoflores.app_pmoviles.views.location.locations.locationsScreen
import com.diegoflores.app_pmoviles.views.location.locations.toLocationsScreen
import kotlinx.serialization.Serializable

@Serializable
data object LocationGraphNavDestination

fun NavController.toLocationGraph(
    destination: LocationGraphNavDestination,
    navOptions: NavOptions? = null
) {
    this.navigate(destination, navOptions)
}

fun NavGraphBuilder.locationGraph(
    navController: NavHostController
) {
    navigation<LocationGraphNavDestination>(startDestination = LocationsDestination) {
        locationsScreen(
            onNavigateLocation = {id->
                navController.toLocationDetailsScreen(
                    destination = LocationDetailsDestination(
                        locationId = id
                    ),
                )
            }
        )

        locationDetailsScreen(
            onNavigateBack = {
                navController.toLocationsScreen(
                    destination = LocationsDestination,
                    navOptions = navOptions {
                        popUpTo(0)
                    }
                )
            }
        )
    }
}