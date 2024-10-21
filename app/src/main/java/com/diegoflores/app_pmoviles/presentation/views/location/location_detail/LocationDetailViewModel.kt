package com.diegoflores.app_pmoviles.views.location.location_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.toRoute
import com.diegoflores.app_pmoviles.data.LocationDb
import com.diegoflores.app_pmoviles.data.repository.LocationRepository
import com.diegoflores.app_pmoviles.di.Dependencies
import com.diegoflores.app_pmoviles.views.character.details.DetailsDestination
import com.diegoflores.app_pmoviles.views.character.details.DetailsScreenState
import com.diegoflores.app_pmoviles.views.location.locations.LocationsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val locationRepository: LocationRepository
): ViewModel(){
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
            val locationDetails = locationRepository.getLocationById(
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])
                val db = Dependencies.provideDatabase(application)
                val savedStateHandle = this.createSavedStateHandle()
                LocationDetailViewModel(
                    savedStateHandle = savedStateHandle,
                    locationRepository = LocationRepository(db.LocationDao())
                )
            }
        }
    }
}