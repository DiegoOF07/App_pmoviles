package com.diegoflores.app_pmoviles

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.diegoflores.app_pmoviles.ui.theme.App_pmovilesTheme
import com.diegoflores.app_pmoviles.navigation.AppNavigation
import com.diegoflores.app_pmoviles.presentation.navigation.AppNavigationViewModel
import com.diegoflores.app_pmoviles.presentation.navigation.AuthStatus

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "userInfo")

class MainActivity : ComponentActivity() {
    private val appNavigationViewModel: AppNavigationViewModel by viewModels { AppNavigationViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen().setKeepOnScreenCondition {
            appNavigationViewModel.authStatus.value is AuthStatus.Loading
        }

        setContent {
            App_pmovilesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.fillMaxSize().padding(innerPadding))
                }
            }
        }
    }
}

