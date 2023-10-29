package com.example.tppokedex.android

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tppokedex.DatabaseDriverFactory
import com.example.tppokedex.android.core.PokedexAdapter
import com.example.tppokedex.data.model.Pokedex
import com.example.tppokedex.android.databinding.ActivityMainBinding
import com.example.tppokedex.android.presentation.PokedexScreenState
import com.example.tppokedex.android.presentation.PokedexViewModel
import com.example.tppokedex.android.presentation.PokedexViewModelFactory
import com.example.tppokedex.data.model.PokedexResults
import com.example.tppokedex.repositoryDB.PokedexDBRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var pokedexAdapter: PokedexAdapter
    private lateinit var viewModel: PokedexViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        // Listen to Retrofit response
        viewModel = ViewModelProvider(this, PokedexViewModelFactory())[PokedexViewModel::class.java]
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.screenState.collect {
                    when (it) {
                        PokedexScreenState.Loading -> showLoading()
                        PokedexScreenState.Error -> handlerError()
                        is PokedexScreenState.ShowPokedex -> showPokedex(it.pokedex)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        pokedexAdapter = PokedexAdapter()

        val gridLayoutManager = GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false)
        with(binding.rvPokedex) {
            this.layoutManager = gridLayoutManager
            this.setHasFixedSize(true)
            this.adapter = pokedexAdapter
        }
    }

    private fun showPokedex(pokedex: Pokedex) {
        binding.pokedexProgressBar.visibility = View.GONE
        pokedexAdapter.updatePokedex(pokedex.results)

        val repositoryPokedexBD = PokedexDBRepository(DatabaseDriverFactory(this))

        // Itera a través de los resultados de la API y realiza la inserción en la base de datos.
        for (result in pokedex.results) {
            // Inserta el nombre y la URL en la tabla "Pokemons".
            repositoryPokedexBD.insertPokemon(result.name, result.url)

        }
    }

    private fun handlerError() {

        //Instancia
        val repositoryPokedexBD  = PokedexDBRepository(databaseDriverFactory = DatabaseDriverFactory(this))
        //Busco en la bd
        val pokemon = repositoryPokedexBD.getAllPokemon()

        if (pokemon.isEmpty()){
            Toast.makeText(this, "Error buscando la informacion", Toast.LENGTH_LONG).show()
        }else{
            binding.pokedexProgressBar.visibility = View.GONE
            pokedexAdapter.updatePokedex(pokemon)
            Toast.makeText(this, "No hay conexion a internet", Toast.LENGTH_LONG).show()
        }
    }

    private fun showLoading() {
        binding.pokedexProgressBar.visibility = View.VISIBLE
    }
}