package com.diegoflores.app_pmoviles.domain

import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    suspend fun logIn()
    suspend fun logOut()
    fun authStatus(): Flow<Boolean>
    suspend fun setName(name: String)
    suspend fun getName(): String?
}