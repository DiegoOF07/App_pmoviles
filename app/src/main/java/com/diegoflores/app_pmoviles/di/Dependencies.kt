package com.diegoflores.app_pmoviles.di

import android.content.Context
import androidx.room.Room
import com.diegoflores.app_pmoviles.data.localDb.AppDatabase
import com.diegoflores.app_pmoviles.data.network.HttpClientFactory
import io.ktor.client.HttpClient


object Dependencies {
    private var database: AppDatabase? = null
    private var httpClient: HttpClient? = null

    private fun buildDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "rick_and_morty.db"
        ).build()
    }

    private fun buildHttpClient(): HttpClient = HttpClientFactory.create()

    fun provideHttpClient(): HttpClient {
        return httpClient ?: synchronized(this) {
            httpClient ?: buildHttpClient().also { httpClient = it }
        }
    }

    fun provideDatabase(context: Context): AppDatabase {
        return database ?: synchronized(this) {
            database ?: buildDatabase(context).also { database = it }
        }
    }


}