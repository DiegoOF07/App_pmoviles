package com.diegoflores.app_pmoviles.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.diegoflores.app_pmoviles.R
import com.diegoflores.app_pmoviles.views.character.characters.CharactersDestination
import com.diegoflores.app_pmoviles.views.location.locations.LocationsDestination
import com.diegoflores.app_pmoviles.views.profile.ProfileDestination

data class NavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val destination: Any,
)

@Composable
fun getNavItems(): List<NavItem>{
    val navItems = listOf(
        NavItem(
            title = "Characters",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_filled_people),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_outlined_people),
            destination = CharacterGraphNavDestination
        ),
        NavItem(
            title = "Locations",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_filled_location),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_outlined_location),
            destination = LocationGraphNavDestination
        ),
        NavItem(
            title = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            destination = ProfileDestination
        )
    )
    return navItems
}

val topLevelDestinations = listOf(
    CharactersDestination::class,
    LocationsDestination::class,
    ProfileDestination::class
)