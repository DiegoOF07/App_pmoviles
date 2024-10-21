package com.diegoflores.app_pmoviles.data.repository

import com.diegoflores.app_pmoviles.data.localDb.dao.CharacterDAO
import com.diegoflores.app_pmoviles.data.localDb.entity.mapToEntity
import com.diegoflores.app_pmoviles.data.localDb.entity.mapToModel
import com.diegoflores.app_pmoviles.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepository(
    private val characterDAO: CharacterDAO
){
    suspend fun getAllCharacters(): List<Character> {
        val characters = characterDAO.getAllCharacters()
        return characters.map { it.mapToModel() }
    }

    suspend fun getCharacterById(id: Int): Character {
        val character = characterDAO.getCharacterById(id)
        return character.mapToModel()
    }

    suspend fun insertAll(characters: List<Character>){
        val characterEntities = characters.map{ it.mapToEntity()}
        characterDAO.insertAll(characterEntities)
    }
}
