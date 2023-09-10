package com.example.tppokedex.android

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PokedexViewModel(private val pokedexRepository: PokedexRepository) : ViewModel() {

    val pokedex = MutableLiveData<Pokedex>()

    private val _screenState: MutableStateFlow<PokedexScreenState> = MutableStateFlow(
        PokedexScreenState.Loading)
    val screenState: Flow<PokedexScreenState> = _screenState

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d("PokedexViewModel", "Error retrieving pokedex: ${throwable.message}")
        }

    init {
        viewModelScope.launch(coroutineExceptionHandler) {
            kotlin.runCatching {
                pokedexRepository.getPokedex()
            }.onSuccess {
                if (it.body() != null) {
                    pokedex.postValue(it.body()!!)
                    _screenState.value = PokedexScreenState.ShowPokedex(it.body()!!)
                } else {
                    _screenState.value = PokedexScreenState.Error
                }
            }.onFailure {
                Log.d("PokedexViewModel", "Error retrieving pokedex: ${it.message}")
                _screenState.value = PokedexScreenState.Error
            }

        }
    }

}
