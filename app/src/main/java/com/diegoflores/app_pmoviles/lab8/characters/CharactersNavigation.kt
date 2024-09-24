package com.diegoflores.app_pmoviles.lab8.characters

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object CharactersDestination

fun NavController.toCharactersScreen(
    destination: CharactersDestination,
    navOptions: NavOptions? = null
) {
    this.navigate(destination, navOptions)
}

fun NavGraphBuilder.charactersScreen(
    onNavigateCharacter: (Int) -> Unit
){
    composable<CharactersDestination> {
        CharactersRoute (
            onNavigateCharacter = onNavigateCharacter
        )
    }
}