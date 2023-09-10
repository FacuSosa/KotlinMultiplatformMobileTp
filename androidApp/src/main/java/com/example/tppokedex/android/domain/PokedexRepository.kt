package com.example.tppokedex.android.domain

import com.example.tppokedex.android.data.model.Pokedex
import retrofit2.Response

interface PokedexRepository {

    suspend fun getPokedex() : Response<Pokedex>
}