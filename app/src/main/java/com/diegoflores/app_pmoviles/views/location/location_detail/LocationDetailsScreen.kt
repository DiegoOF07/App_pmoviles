package com.diegoflores.app_pmoviles.views.location.location_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.diegoflores.app_pmoviles.data.LocationDb
import com.diegoflores.app_pmoviles.ui.theme.App_pmovilesTheme

@Composable
fun LocationDetailsRoute(
    id: Int,
    onNavigateBack: ()->Unit
){
    LocationDetailsScreen(
        id = id,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationDetailsScreen(
    id: Int,
    onNavigateBack: ()-> Unit
){
    val locationDb = LocationDb()
    val locationSelected = locationDb.getLocationById(id)

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

            Text(text = locationSelected.name,
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
                    Text(text = "${locationSelected.id}")
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = "Type: ")
                    Text(text = locationSelected.type)
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = "Dimensions: ")
                    Text(text = locationSelected.dimension)
                }
            }
        }

    }
}

@Preview
@Composable
private fun PreviewLocationDetailsScreen(){
    App_pmovilesTheme{
        Surface {
            LocationDetailsScreen(id = 1, onNavigateBack = {})
        }
    }
}