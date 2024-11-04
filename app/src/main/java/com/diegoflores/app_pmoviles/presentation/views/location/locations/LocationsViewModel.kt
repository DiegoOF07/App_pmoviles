package com.diegoflores.app_pmoviles.views.location.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.diegoflores.app_pmoviles.data.LocationDb
import com.diegoflores.app_pmoviles.data.network.KtorRickAndMortyApi
import com.diegoflores.app_pmoviles.data.network.dto.mapToCharacterModel
import com.diegoflores.app_pmoviles.data.network.dto.mapToLocationModel
import com.diegoflores.app_pmoviles.data.repository.CharacterRepository
import com.diegoflores.app_pmoviles.data.repository.LocationRepository
import com.diegoflores.app_pmoviles.di.Dependencies
import com.diegoflores.app_pmoviles.domain.network.RickAndMortyApi
import com.diegoflores.app_pmoviles.domain.network.util.map
import com.diegoflores.app_pmoviles.domain.network.util.onError
import com.diegoflores.app_pmoviles.domain.network.util.onSuccess
import com.diegoflores.app_pmoviles.views.character.characters.CharactersViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationsViewModel(
    private val locationRepository: LocationRepository,
    private val rickAndMortyApi: RickAndMortyApi
): ViewModel(){
    private val locationDb = LocationDb()
    private val _state = MutableStateFlow(LocationsScreenState())
    val state = _state.asStateFlow()

    init {
        loadLocationsFromApi()
        getLocationsFromRoom()
    }

    fun loadLocationsFromLocalDb(){
        if(_state.value.data.isEmpty()){
            viewModelScope.launch {
                locationRepository.insertAll(locationDb.getAllLocations())
            }
        }
    }

    fun loadLocationsFromApi(){
        viewModelScope.launch {
            if(locationRepository.getAllLocations().isEmpty()){
                rickAndMortyApi
                    .getAllLocations()
                    .map { response ->
                        _state.update {
                            it.copy(
                                isLoading = true,
                                hasError = false,
                            )
                        }
                        response.results.map { it.mapToLocationModel() }
                    }
                    .onSuccess { locations ->
                        locationRepository.insertAll(locations)
                        _state.update {
                            it.copy(
                                isLoading = false,
                                hasError = false
                            )
                        }
                    }
                    .onError { error ->
                        _state.update {
                            it.copy(
                                hasError = true,
                                isLoading = false
                            )
                        }
                        println(error)
                    }
            }
        }
    }

    fun getLocationsFromRoom(){
        viewModelScope.launch {
            _state.update { it.copy(
                hasError = false,
                isLoading = true
            ) }
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
                val api = KtorRickAndMortyApi(Dependencies.provideHttpClient())
                LocationsViewModel(
                    locationRepository = LocationRepository(db.LocationDao()),
                    rickAndMortyApi = api
                )
            }
        }
    }
}