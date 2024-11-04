package com.diegoflores.app_pmoviles.data.network

import com.diegoflores.app_pmoviles.data.network.dto.CharacterProfileDto
import com.diegoflores.app_pmoviles.data.network.dto.CharactersListDto
import com.diegoflores.app_pmoviles.data.network.dto.LocationDetailsDto
import com.diegoflores.app_pmoviles.data.network.dto.LocationsListDto
import com.diegoflores.app_pmoviles.data.network.util.safeCall
import com.diegoflores.app_pmoviles.domain.network.RickAndMortyApi
import com.diegoflores.app_pmoviles.domain.network.util.NetworkError
import com.diegoflores.app_pmoviles.domain.network.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class KtorRickAndMortyApi (
    private val httpClient: HttpClient
): RickAndMortyApi {
    override suspend fun getAllCharacters(): Result<CharactersListDto, NetworkError> {
        return safeCall<CharactersListDto>{
            httpClient.get(
                "https://rickandmortyapi.com/api/character"
            )
        }
    }

    override suspend fun getCharacterProfile(id: Int): Result<CharacterProfileDto, NetworkError> {
        return safeCall<CharacterProfileDto>{
            httpClient.get(
                "https://rickandmortyapi.com/api/character/${id}"
            )
        }
    }

    override suspend fun getAllLocations(): Result<LocationsListDto, NetworkError> {
        return safeCall<LocationsListDto>{
            httpClient.get(
                "https://rickandmortyapi.com/api/location"
            )
        }
    }

    override suspend fun getLocationDetails(id: Int): Result<LocationDetailsDto, NetworkError> {
        return safeCall<LocationDetailsDto>{
            httpClient.get(
                "https://rickandmortyapi.com/api/location/${id}"
            )
        }
    }

}
