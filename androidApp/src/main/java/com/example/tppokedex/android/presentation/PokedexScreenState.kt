package com.example.tppokedex.android.presentation

import com.example.tppokedex.data.model.Pokedex
import com.example.tppokedex.repositoryDB.PokedexDBRepository

sealed class PokedexScreenState {

    object Loading : PokedexScreenState()

    object Error : PokedexScreenState()

    class ShowPokedex(val pokedex : Pokedex) : PokedexScreenState()


}
