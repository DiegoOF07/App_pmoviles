package com.diegoflores.app_pmoviles.views.location.location_detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diegoflores.app_pmoviles.ui.theme.App_pmovilesTheme

@Composable
fun LocationDetailsRoute(
    viewModel: LocationDetailViewModel = viewModel(factory = LocationDetailViewModel.Factory),
    onNavigateBack: ()->Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    LocationDetailsScreen(
        state = state,
        onGenerateError = {viewModel.simulateError()},
        onReloadData = {viewModel.getLocationDetailsData()},
        onNavigateBack = onNavigateBack
    )
}

@Composable
private fun LocationDetailsScreen(
    state: LocationDetailsScreenState,
    onGenerateError: ()->Unit,
    onReloadData: ()->Unit,
    onNavigateBack: ()-> Unit
){
    when{
        state.isLoading -> LoadingScreen(onGenerateError)
        state.hasError -> ErrorScreen(onReloadData)
        else -> state.data?.let {
            LocationDetailsContent(
                name = it.name,
                id = state.data.id,
                type = state.data.type,
                dimension = state.data.dimension,
                onNavigateBack)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationDetailsContent(
    name: String,
    id: Int,
    type: String,
    dimension: String,
    onNavigateBack: () -> Unit
){
    Column (
        modifier = Modifier
            .fillMaxSize()
    ){
        TopAppBar(
            title = {
                Text("Location Detail")
            },
            windowInsets = WindowInsets(0.dp, 12.dp, 0.dp,0.dp),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Text(text = name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 50.dp),
            )

            Column (
                modifier = Modifier
                    .width(250.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ){
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = "ID: ")
                    Text(text = "$id")
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = "Type: ")
                    Text(text = type)
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = "Dimensions: ")
                    Text(text = dimension)
                }
            }
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
            Icon(Icons.Outlined.Info,
                contentDescription =null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(48.dp))
            Text(text = "Error al obtener la información de la localización\nIntenta de nuevo",
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
private fun PreviewLocationDetailsScreen(){
    App_pmovilesTheme{
        Surface {
            LocationDetailsScreen(
                state = LocationDetailsScreenState(),
                onGenerateError = {},
                onReloadData = {},
                onNavigateBack = {}
            )
        }
    }
}