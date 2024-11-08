package com.diegoflores.app_pmoviles.views.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diegoflores.app_pmoviles.R
import com.diegoflores.app_pmoviles.presentation.views.profile.ProfileScreenState
import com.diegoflores.app_pmoviles.presentation.views.profile.ProfileViewModel
import com.diegoflores.app_pmoviles.ui.theme.App_pmovilesTheme

@Composable
fun ProfileRoute(
    onLogOut: ()-> Unit,
    viewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory)){

    val state by viewModel.state.collectAsStateWithLifecycle()
    ProfileScreen(
        state= state,
        onLogOut = {
            viewModel.logOut()
            onLogOut()})
}

@Composable
private fun ProfileScreen(state: ProfileScreenState, onLogOut: () -> Unit){
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(170.dp),
            contentAlignment = Alignment.BottomCenter){
            Text(text = "Tu perfil", style = MaterialTheme.typography.headlineLarge)
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(230.dp),
            contentAlignment = Alignment.BottomCenter){
            ProfileImage(size = 200)
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 48.dp)){
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(60.dp, 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Nombre:")
                Text(text = state.name?: "Anonimo")
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(60.dp, 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Carné:")
                Text(text = "23714")
            }
        }

        Box(modifier = Modifier){
            OutlinedButton(onClick = onLogOut) {
                Text(text = "Cerrar Sesión")
            }
        }

    }
}

@Composable
private fun ProfileImage(size: Int){
    Box(modifier = Modifier.size(size.dp),
        contentAlignment = Alignment.BottomEnd){
        Image(painter = painterResource(id = R.drawable.im_profile),
            contentDescription = "profile_img",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(size.dp)
                .border(BorderStroke(4.dp, MaterialTheme.colorScheme.primary), CircleShape)
                .padding(4.dp)
                .clip(CircleShape))
        Box(
            modifier = Modifier
                .size((size / 10).dp)
                .offset(x = (-(size / 10)).dp, y = (-14).dp)
                .clip(CircleShape)
                .background(Color.Green)
                .border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape)

        )
    }
}


@Preview
@Composable
private fun PreviewProfileScreen() {
    App_pmovilesTheme{
        Surface(modifier = Modifier.fillMaxSize()) {
            ProfileScreen(
                state = ProfileScreenState(),
                onLogOut = {})
        }
    }
}