package com.diegoflores.app_pmoviles.views.location.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegoflores.app_pmoviles.data.LocationDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationsViewModel: ViewModel(){
    private val locationDb = LocationDb()
    private val _state = MutableStateFlow(LocationsScreenState())
    val state = _state.asStateFlow()

    init {
        getLocationsData()
    }

    fun getLocationsData(){
        viewModelScope.launch {
            _state.update { it.copy(
                hasError = false,
                isLoading = true
            ) }

            delay(4000L)
            val locations = locationDb.getAllLocations()

            _state.update { it.copy(
                isLoading = false,
                data = locations
            ) }
        }
    }

    fun simulateError(){
        _state.update { it.copy(
            hasError = true,
            isLoading = false
        ) }
    }
}