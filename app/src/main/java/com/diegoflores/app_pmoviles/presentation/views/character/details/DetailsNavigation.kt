package com.diegoflores.app_pmoviles.views.character.details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class DetailsDestination(
    val characterId : Int
)

fun NavController.toDetailsScreen(
    destination: DetailsDestination,
    navOptions: NavOptions? = null
) {
    this.navigate(destination, navOptions)
}

fun NavGraphBuilder.detailsScreen(
    onNavigateBack: () -> Unit
) {
    composable<DetailsDestination> {
        DetailsRoute(
            onNavigateBack = onNavigateBack
        )
    }
}