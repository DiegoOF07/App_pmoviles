package com.diegoflores.app_pmoviles.views.location.locations

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diegoflores.app_pmoviles.domain.model.Location
import com.diegoflores.app_pmoviles.ui.theme.App_pmovilesTheme

@Composable
fun LocationsRoute(
    viewModel: LocationsViewModel = viewModel(factory = LocationsViewModel.Factory),
    onNavigateLocation: (Int) -> Unit,
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    LocationsScreen (
        state = state,
        onGenerateError = {viewModel.simulateError()},
        onReloadData = {viewModel.getLocationsData()},
        onNavigateLocation = onNavigateLocation,
    )
}

@Composable
private fun LocationsScreen(
    state: LocationsScreenState,
    onGenerateError: ()->Unit,
    onReloadData: ()->Unit,
    onNavigateLocation: (Int) -> Unit,
){
    when{
        state.isLoading-> LoadingScreen(onGenerateError)
        state.hasError-> ErrorScreen(onReloadData)
        state.data.isNotEmpty()-> LocationsListContent(
            locations = state.data,
            onNavigateLocation)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationsListContent(
    locations: List<Location>,
    onNavigateLocation: (Int) -> Unit
){
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

@Composable
private fun LoadingScreen(onGenerateError: ()->Unit){
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable(onClick = onGenerateError),
        contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorScreen(onReloadData: () -> Unit){
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        Column(modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp)) {
            Icon(
                Icons.Outlined.Info,
                contentDescription =null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(48.dp))
            Text(text = "Error al obtener el listado de localizaciones\nIntenta de nuevo",
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
            OutlinedButton(onClick = onReloadData) {
                Text(text = "Reintentar", color = MaterialTheme.colorScheme.error)
            }

        }
    }
}

@Preview
@Composable
private fun PreviewLocationsScreen(){
    App_pmovilesTheme{
        Surface {
            LocationsScreen(
                state = LocationsScreenState(),
                onGenerateError = {},
                onReloadData = {},
                onNavigateLocation = {})
        }
    }
}