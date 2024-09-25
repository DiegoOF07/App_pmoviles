package com.diegoflores.app_pmoviles.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.navigation
import androidx.navigation.navOptions
import com.diegoflores.app_pmoviles.views.character.characters.CharactersDestination
import com.diegoflores.app_pmoviles.views.character.characters.charactersScreen
import com.diegoflores.app_pmoviles.views.character.characters.toCharactersScreen
import com.diegoflores.app_pmoviles.views.character.details.DetailsDestination
import com.diegoflores.app_pmoviles.views.character.details.detailsScreen
import com.diegoflores.app_pmoviles.views.character.details.toDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data object CharacterGraphNavDestination

fun NavController.toCharacterGraph(
    destination: CharacterGraphNavDestination,
    navOptions: NavOptions? = null
) {
    this.navigate(destination, navOptions)
}

fun NavGraphBuilder.characterGraph(
    navController: NavHostController
) {
    navigation<CharacterGraphNavDestination>(startDestination = CharactersDestination) {
        charactersScreen(
            onNavigateCharacter = {id->
                navController.toDetailsScreen(
                    destination = DetailsDestination(
                        characterId = id
                    ),
                )
            }
        )

        detailsScreen(
            onNavigateBack = {
                navController.toCharactersScreen(
                    destination = CharactersDestination,
                    navOptions = navOptions {
                        popUpTo(0)
                    }
                )
            }
        )

    }
}