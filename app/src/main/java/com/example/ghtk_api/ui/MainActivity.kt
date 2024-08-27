package com.example.ghtk_api.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ghtk_api.adapter.PokemonAdapter
import com.example.ghtk_api.databinding.ActivityMainBinding
import com.example.ghtk_api.db.PokemonDao
import com.example.ghtk_api.db.PokemonDatabase
import com.example.ghtk_api.viewmodels.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Inject the adapter
    @Inject
    lateinit var adapter: PokemonAdapter

    private val viewModel: PokemonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rcv.layoutManager = GridLayoutManager(this, 2)
        binding.rcv.adapter = adapter

        viewModel.fetchData()
        Log.e("TAG", viewModel.pokemonList.value.toString())
        val pokemonDao: PokemonDao = PokemonDatabase.getDatabase(this).pokemonDao()
        viewModel.pokemonList.observe(this) { pokemons ->
            if (pokemons != null) {
                lifecycleScope.launch(Dispatchers.IO) {
                    if (pokemonDao.getAllPokemon().isEmpty()) {
                        pokemonDao.insertAll(pokemons)
                    }

                    withContext(Dispatchers.Main) {
                        adapter.addPokemons(pokemons)
                    }
                }
            }
        }

        viewModel.isConnected.observe(this) { isConnected ->
            if (isConnected) {
                // Có thể load dữ liệu từ API
                viewModel.currentOffset = 20
            } else {
                lifecycleScope.launch(Dispatchers.IO) {
                    val localPokemons = pokemonDao.getAllPokemon()
                    withContext(Dispatchers.Main) {
                        adapter.updateList(localPokemons)
                    }
                }
            }
        }


        binding.rcv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                if (layoutManager.findLastVisibleItemPosition() == adapter.itemCount - 1) {
                    viewModel.loadMoreData()
                }
            }
        })
    }
}
