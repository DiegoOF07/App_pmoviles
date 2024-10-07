package com.diegoflores.app_pmoviles.views.character.characters

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.diegoflores.app_pmoviles.data.CharacterDb
import com.diegoflores.app_pmoviles.data.Character
import com.diegoflores.app_pmoviles.ui.theme.App_pmovilesTheme

@Composable
fun CharactersRoute(
    viewModel: CharactersViewModel = viewModel(),
    onNavigateCharacter: (Int) -> Unit,
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    CharactersScreen (
        state = state,
        onGenerateError = {viewModel.simulateError()},
        onReloadData = {viewModel.getCharactersData()},
        onNavigateCharacter = onNavigateCharacter,
    )
}


@Composable
private fun CharactersScreen(
    state: CharactersScreenState,
    onGenerateError: () -> Unit,
    onReloadData: ()->Unit,
    onNavigateCharacter: (Int) -> Unit,
){
    when{
        state.isLoading-> LoadingScreen(onGenerateError)
        state.hasError-> ErrorScreen(onReloadData)
        state.data.isNotEmpty() -> CharacterListContent(characters = state.data, onNavigateCharacter = onNavigateCharacter)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterListContent(
    characters: List<Character>,
    onNavigateCharacter: (Int) -> Unit){
    Column (modifier = Modifier.fillMaxSize()){
        TopAppBar(
            title = {
                Text("Characters")
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
            items(characters) { character ->
                CharacterItem(character, onPressed = { onNavigateCharacter(character.id) })
            }
        }
    }
}

@Composable
fun CharacterItem(character: Character, onPressed: () -> Unit) {
    Spacer(modifier = Modifier.height(25.dp))
    Row (modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .clip(RoundedCornerShape(20))
        .clickable { onPressed() },
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(modifier = Modifier.padding(start = 12.dp)){
            AsyncImage(
                model = character.image,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentScale = ContentScale.Crop
            )
        }

        Column(modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = character.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "${character.species} - ${character.status}", style = MaterialTheme.typography.bodyMedium)
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
            Text(text = "Error al obtener el listado de personajes\nIntenta de nuevo",
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
private fun PreviewCharactersScreen(){
    App_pmovilesTheme{
        Surface {
            CharactersScreen(
                state = CharactersScreenState(),
                onGenerateError = {},
                onReloadData = {},
                onNavigateCharacter = {})
        }
    }
}