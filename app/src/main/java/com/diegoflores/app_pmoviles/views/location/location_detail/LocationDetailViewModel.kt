package com.diegoflores.app_pmoviles.views.location.location_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.diegoflores.app_pmoviles.data.LocationDb
import com.diegoflores.app_pmoviles.views.character.details.DetailsDestination
import com.diegoflores.app_pmoviles.views.character.details.DetailsScreenState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationDetailViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel(){
    private val locationDb = LocationDb()
    private val locationSelected = savedStateHandle.toRoute<LocationDetailsDestination>()
    private val _state = MutableStateFlow(LocationDetailsScreenState())
    val state = _state.asStateFlow()

    init {
        getLocationDetailsData()
    }

    fun getLocationDetailsData(){
        viewModelScope.launch {
            _state.update { it.copy(
                hasError = false,
                isLoading = true
            ) }

            delay(2000L)
            val locationDetails = locationDb.getLocationById(
                locationSelected.locationId
            )

            _state.update { it.copy(
                isLoading = false,
                data = locationDetails
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