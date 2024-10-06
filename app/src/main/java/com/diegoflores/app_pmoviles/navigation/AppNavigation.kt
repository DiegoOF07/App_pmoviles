package com.diegoflores.app_pmoviles.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.diegoflores.app_pmoviles.views.character.login.LoginDestination
import com.diegoflores.app_pmoviles.views.character.login.loginScreen
import com.diegoflores.app_pmoviles.views.character.login.toLogin
import com.diegoflores.app_pmoviles.views.profile.profileScreen
@Composable
fun AppNavigation(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    var navBarState by rememberSaveable {
        mutableStateOf(true)
    }
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    navBarState = if (currentDestination != null) {
        topLevelDestinations.any { destination ->
            currentDestination.hasRoute(destination)
        }
    } else {
        false
    }
    Scaffold(bottomBar = {
        AnimatedVisibility(
            visible = navBarState,
            enter =
                slideInVertically(initialOffsetY = { it })+
                        expandVertically(expandFrom = Alignment.Top) +
                        fadeIn(initialAlpha = 0.3f),
            exit = slideOutVertically(targetOffsetY = { it })+
                    shrinkVertically() +
                    fadeOut()
        ){
            BottomNavigationBar(
                checkItemSelected = {destination ->
                    currentDestination?.hierarchy?.any{
                        it.hasRoute(destination::class)
                    }?:false
                },
                onNavigationItem = {destination->
                    navController.navigate(destination){
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
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
                            popUpTo(0)
                        }
                    )
                }
            )



        }

    }
}

@Composable
fun BottomNavigationBar(
    checkItemSelected: (Any) -> Boolean,
    onNavigationItem: (Any) -> Unit
){
    val navItems = getNavItems()
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        navItems.forEach { navItem ->
            val isSelected = checkItemSelected(navItem.destination)
            NavigationBarItem(
                selected = isSelected,
                label = { Text(navItem.title, color = MaterialTheme.colorScheme.onPrimary) },
                onClick = {
                    onNavigationItem(navItem.destination)
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) {
                            navItem.selectedIcon
                        } else navItem.unselectedIcon,
                        contentDescription = navItem.title,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    selectedIndicatorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    disabledIconColor = MaterialTheme.colorScheme.onSecondary,
                    disabledTextColor = MaterialTheme.colorScheme.onSecondary
                )
            )
        }
    }
}




