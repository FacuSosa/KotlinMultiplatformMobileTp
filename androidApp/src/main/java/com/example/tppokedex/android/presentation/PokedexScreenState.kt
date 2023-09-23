package com.example.tppokedex.android.presentation

import com.example.tppokedex.data.model.Pokedex

sealed class PokedexScreenState {

    object Loading : PokedexScreenState()

    object Error : PokedexScreenState()

    class ShowPokedex(val pokedex : Pokedex) : PokedexScreenState()

}
