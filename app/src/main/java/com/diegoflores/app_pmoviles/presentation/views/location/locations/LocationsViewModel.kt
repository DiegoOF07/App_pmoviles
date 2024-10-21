package com.diegoflores.app_pmoviles.views.location.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.diegoflores.app_pmoviles.data.LocationDb
import com.diegoflores.app_pmoviles.data.repository.CharacterRepository
import com.diegoflores.app_pmoviles.data.repository.LocationRepository
import com.diegoflores.app_pmoviles.di.Dependencies
import com.diegoflores.app_pmoviles.views.character.characters.CharactersViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationsViewModel(
    private val locationRepository: LocationRepository
): ViewModel(){
    private val locationDb = LocationDb()
    private val _state = MutableStateFlow(LocationsScreenState())
    val state = _state.asStateFlow()

    init {
        loadData()
        getLocationsData()
    }

    private fun loadData(){
        if(_state.value.data.isEmpty()){
            viewModelScope.launch {
                locationRepository.insertAll(locationDb.getAllLocations())
            }
        }
    }

    fun getLocationsData(){
        viewModelScope.launch {
            _state.update { it.copy(
                hasError = false,
                isLoading = true
            ) }

            delay(4000L)
            val locations = locationRepository.getAllLocations()

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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])
                val db = Dependencies.provideDatabase(application)
                LocationsViewModel(
                    locationRepository = LocationRepository(db.LocationDao())
                )
            }
        }
    }
}