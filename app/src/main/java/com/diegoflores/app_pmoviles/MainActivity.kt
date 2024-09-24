package com.diegoflores.app_pmoviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import com.diegoflores.app_pmoviles.ui.theme.App_pmovilesTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.diegoflores.app_pmoviles.lab8.characters.CharactersDestination
import com.diegoflores.app_pmoviles.lab8.characters.charactersScreen
import com.diegoflores.app_pmoviles.lab8.characters.toCharactersScreen
import com.diegoflores.app_pmoviles.lab8.details.DetailsDestination
import com.diegoflores.app_pmoviles.lab8.details.detailsScreen
import com.diegoflores.app_pmoviles.lab8.details.toDetailsScreen
import com.diegoflores.app_pmoviles.lab8.login.LoginDestination
import com.diegoflores.app_pmoviles.lab8.login.loginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App_pmovilesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController = navController,
                        startDestination = LoginDestination,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)) {

                        loginScreen (onNavigateLogin = {
                            navController.toCharactersScreen(
                                destination = CharactersDestination,
                                navOptions = navOptions{
                                    popUpTo<LoginDestination>() {inclusive = true}
                                }
                            )
                        })

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
            }
        }
    }
}

