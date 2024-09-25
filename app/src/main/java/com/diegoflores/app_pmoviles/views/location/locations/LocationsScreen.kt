package com.diegoflores.app_pmoviles.views.location.locations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.diegoflores.app_pmoviles.data.Character
import com.diegoflores.app_pmoviles.data.CharacterDb
import com.diegoflores.app_pmoviles.data.Location
import com.diegoflores.app_pmoviles.data.LocationDb
import com.diegoflores.app_pmoviles.ui.theme.App_pmovilesTheme

@Composable
fun LocationsRoute(
    onNavigateLocation: (Int) -> Unit,
){
    LocationsScreen (
        onNavigateLocation = onNavigateLocation,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationsScreen(
    onNavigateLocation: (Int) -> Unit,
){
    val locationDb = LocationDb()
    val locations = locationDb.getAllLocations()

    Column (modifier = Modifier.fillMaxSize()){
        TopAppBar(
            title = {
                Text("Locations")
            },
            windowInsets = WindowInsets(0.dp, 12.dp, 0.dp,0.dp),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.primary,
            )
        )

        LazyColumn (
            modifier = Modifier.padding(horizontal = 20.dp),
        ){
            items(locations) { location ->
                LocationItem(location, onPressed = { onNavigateLocation(location.id) })
            }
        }
    }
}

@Composable
fun LocationItem(location: Location, onPressed: () -> Unit) {
    Spacer(modifier = Modifier.height(25.dp))
    Row (modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .clip(RoundedCornerShape(20))
        .clickable { onPressed() },
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(modifier = Modifier.padding(start = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = location.name, style = MaterialTheme.typography.titleMedium)
            Text(text = location.type, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview
@Composable
private fun PreviewLocationsScreen(){
    App_pmovilesTheme{
        Surface {
            LocationsScreen(onNavigateLocation = {})
        }
    }
}