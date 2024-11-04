package com.diegoflores.app_pmoviles.domain.network

import com.diegoflores.app_pmoviles.data.network.dto.CharacterProfileDto
import com.diegoflores.app_pmoviles.data.network.dto.CharactersListDto
import com.diegoflores.app_pmoviles.data.network.dto.LocationDetailsDto
import com.diegoflores.app_pmoviles.data.network.dto.LocationsListDto
import com.diegoflores.app_pmoviles.domain.network.util.NetworkError
import com.diegoflores.app_pmoviles.domain.network.util.Result

interface RickAndMortyApi {
    suspend fun getAllCharacters(): Result<CharactersListDto, NetworkError>
    suspend fun getCharacterProfile(id: Int): Result<CharacterProfileDto, NetworkError>
    suspend fun getAllLocations(): Result<LocationsListDto, NetworkError>
    suspend fun getLocationDetails(id: Int): Result<LocationDetailsDto, NetworkError>
}