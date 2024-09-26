package com.diegoflores.app_pmoviles.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.diegoflores.app_pmoviles.R
import com.diegoflores.app_pmoviles.ui.theme.App_pmovilesTheme
import com.diegoflores.app_pmoviles.views.character.login.LoginDestination
import com.diegoflores.app_pmoviles.views.character.login.loginScreen
import com.diegoflores.app_pmoviles.views.character.login.toLogin
import com.diegoflores.app_pmoviles.views.profile.ProfileDestination
import com.diegoflores.app_pmoviles.views.profile.profileScreen
import com.diegoflores.app_pmoviles.views.profile.toProfileScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    var navBarState by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(bottomBar = {
        if(navBarState) MainBottomAppBar(navController)
    }){innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LoginDestination,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)) {

            loginScreen (onNavigateLogin = {
                navController.toCharacterGraph(
                    destination = CharacterGraphNavDestination,
                )
                navBarState = true
            })

            characterGraph(navController = navController)

            locationGraph(navController = navController)

            profileScreen(onLogOut = {
                    navController.toLogin(
                        destination = LoginDestination,
                        navOptions = navOptions {
                            popUpTo<LoginDestination>() {inclusive = true}
                        }
                    )
                navBarState = false
                }
            )



        }

    }
}

@Composable
fun MainBottomAppBar(navController: NavHostController){
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        NavigationBarItem(
            selected = false,
            onClick = {navController.toCharacterGraph(
                destination = CharacterGraphNavDestination
            )},
            icon = {
                Icon(painter = painterResource(id = R.drawable.ic_people),
                    contentDescription = "Characters",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(48.dp))
            })
        NavigationBarItem(
            selected = false,
            onClick = {navController.toLocationGraph(
                destination = LocationGraphNavDestination
            )},
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_world),
                    contentDescription = "Locations",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(48.dp)
                )
            })
        NavigationBarItem(
            selected = false,
            onClick = {navController.toProfileScreen(
                destination = ProfileDestination
            )},
            icon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Profile",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(48.dp)
                )
            })
    }
}

@Preview
@Composable
private fun PreviewMainBottomAppBar(){
    App_pmovilesTheme{
        Surface() {
            MainBottomAppBar(navController = rememberNavController())
        }
    }
}
