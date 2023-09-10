package com.example.tppokedex.android

import retrofit2.Response

interface PokedexRepository {

    suspend fun getPokedex() : Response<Pokedex>
}