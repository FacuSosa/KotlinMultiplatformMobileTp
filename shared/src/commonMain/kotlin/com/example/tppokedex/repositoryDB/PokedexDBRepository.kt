package com.example.tppokedex.repositoryDB

import com.example.tppokedex.DatabaseDriverFactory
import com.example.tppokedex.data.model.NameData
import com.example.tppokedex.data.model.PictureData
import com.example.tppokedex.data.model.PokedexResults
import com.example.tppokedex.data.model.RandomData
import com.pokedex.db.AppDatabase
import compokedexdb.Pokemon

class PokedexDBRepository(databaseDriverFactory: DatabaseDriverFactory) {

     private val database = AppDatabase(databaseDriverFactory.createDriver())
     private val dbQuery = database.pokedexQueries

  fun getAllpokemons():List <PokedexResults>{
      return dbQuery.selectAllPokemon(::mapPokemonSelecting).executeAsList()
  }
    private fun mapPokemonSelecting(
        mapName:String,
        mapUrl:String
    ):PokedexResults{
        return PokedexResults(name = mapName, url = mapUrl)
    }
    fun insertPokemon(pokemon: PokedexResults){
        dbQuery.insertPokemon(name = pokemon.name, url = pokemon.url)
    }
}