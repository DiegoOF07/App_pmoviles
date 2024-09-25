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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    Scaffold(bottomBar = {
        MainBottomAppBar(navController)
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
                }
            )



        }

    }
}

@Composable
fun MainBottomAppBar(navController: NavHostController) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.primary)
        .padding(bottom = 30.dp, top = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround){
        Column(modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = {navController.toCharacterGraph(
                destination = CharacterGraphNavDestination
            )}) {
                Icon(painter = painterResource(id = R.drawable.ic_people),
                    contentDescription = "Characters",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(48.dp))
            }
            Text(text = "Characters",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimary)
        }
        Column(modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = {navController.toLocationGraph(
                destination = LocationGraphNavDestination
            )}) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_world),
                    contentDescription = "Locations",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(48.dp)
                )
            }
            Text(text = "Locations",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimary)
        }
        Column(modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = {navController.toProfileScreen(
                destination = ProfileDestination
            )}) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(48.dp)
                )
            }
            Text(text = "Profile",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

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
