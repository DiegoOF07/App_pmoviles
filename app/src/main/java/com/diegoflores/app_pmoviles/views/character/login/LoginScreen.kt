package com.diegoflores.app_pmoviles.views.character.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diegoflores.app_pmoviles.R
import com.diegoflores.app_pmoviles.ui.theme.App_pmovilesTheme

@Composable
fun LoginRoute(
    onNavigateLogin: () -> Unit,
) {
    LoginScreen(
        onNavigateLogin = onNavigateLogin
    )
}

@Composable
private fun LoginScreen(
    onNavigateLogin: () -> Unit
) {
    Column(modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.im_main_logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 12.dp)
        )
        Button(onClick = onNavigateLogin,
            modifier = Modifier
                .fillMaxWidth()
                .padding(48.dp, 12.dp)) {
            Text("Entrar")
        }
    }
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom) {
        Text("Diego Oswaldo Flores Rivas - 23714",
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            textAlign = TextAlign.Center)
    }
}

@Preview
@Composable
private fun PreviewLoginScreen() {
    App_pmovilesTheme{
        Surface(modifier = Modifier.fillMaxSize()) {
            LoginScreen(onNavigateLogin = {})
        }
    }
}