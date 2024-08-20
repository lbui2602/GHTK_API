package com.example.ghtk_api.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ghtk_api.adapter.PokemonAdapter
import com.example.ghtk_api.databinding.ActivityMainBinding
import com.example.ghtk_api.viewmodels.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint
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

        viewModel.pokemonList.observe(this) { pokemons ->
            if (pokemons != null) {
                adapter.addPokemons(pokemons)
                Log.e("TAG", "true")
            }
            Log.e("TAG", pokemons?.size.toString())
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
