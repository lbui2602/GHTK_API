package com.example.ghtk_api.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ghtk_api.R
import com.example.ghtk_api.adapter.PokemonAdapter
import com.example.ghtk_api.api.ApiService
import com.example.ghtk_api.databinding.ActivityMainBinding
import com.example.ghtk_api.models.PokemonResponse
import com.example.ghtk_api.viewmodels.PokemonViewModel
import kotlinx.coroutines.CoroutineScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: PokemonViewModel by viewModels()
    private lateinit var adapter: PokemonAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = PokemonAdapter(mutableListOf())
        binding.rcv.layoutManager = GridLayoutManager(this,2)
        binding.rcv.adapter = adapter
        viewModel.fetchData()
        viewModel.pokemonList.observe(this) { pokemons ->
            if (pokemons != null) {
                adapter.addPokemons(pokemons)
                Log.e("TAG","true")
            }
            Log.e("TAG",pokemons?.size.toString())
        }
        binding.rcv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastVisibleItemPosition() == adapter.itemCount - 1) {
                    viewModel.loadMoreData()
                }
            }
        })
    }
}
