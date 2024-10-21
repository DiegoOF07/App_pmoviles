package com.diegoflores.app_pmoviles.data.localDb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.diegoflores.app_pmoviles.data.localDb.dao.CharacterDAO
import com.diegoflores.app_pmoviles.data.localDb.dao.LocationDAO
import com.diegoflores.app_pmoviles.data.localDb.entity.CharacterEntity
import com.diegoflores.app_pmoviles.data.localDb.entity.LocationEntity


@Database(entities = [CharacterEntity::class, LocationEntity::class],version = 1)
abstract class AppDatabase(): RoomDatabase(){
    abstract fun CharacterDao(): CharacterDAO
    abstract fun LocationDao(): LocationDAO
}