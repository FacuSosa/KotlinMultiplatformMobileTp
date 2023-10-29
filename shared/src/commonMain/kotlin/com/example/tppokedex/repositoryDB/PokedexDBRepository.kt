package com.example.tppokedex.repositoryDB

import com.example.tppokedex.DatabaseDriverFactory
import com.example.tppokedex.data.model.PokedexResults
import com.pokedex.db.AppDatabase


class PokedexDBRepository(databaseDriverFactory: DatabaseDriverFactory) {

     private val database = AppDatabase(databaseDriverFactory.createDriver())
     private val dbQuery = database.pokedexQueries

    fun insertPokemon(name: String, url:String){
        dbQuery.transaction {
            dbQuery.insertPokemon(name = name, url = url)
        }
    }
    fun getAllPokemon(): List <PokedexResults> {
        val results: List <PokedexResults> = dbQuery.selectAllPokemon(){
                name, url -> PokedexResults(name,url)
        }.executeAsList()

        return results
    }
}