package com.example.tppokedex.android.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tppokedex.data.remote.PokedexRepository

class PokedexViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val pokedexRepository = PokedexRepository()

        return PokedexViewModel(pokedexRepository) as T
    }
}