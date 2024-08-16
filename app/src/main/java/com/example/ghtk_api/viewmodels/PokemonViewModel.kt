package com.example.ghtk_api.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghtk_api.repository.PokemonRepository
import com.example.ghtk_api.models.PokemonResponse
import com.example.ghtk_api.models.Result
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {
    private val repository = PokemonRepository()
    private val _pokemonList = MutableLiveData<List<Result>?>()
    val pokemonList: MutableLiveData<List<Result>?> get() = _pokemonList

    private val _pokemonListLoadMore = MutableLiveData<List<Result>?>()
    val pokemonListLoadMore: MutableLiveData<List<Result>?> get() = _pokemonListLoadMore

    private var currentOffset = 20
    private val pageSize = 20
    private var isLoading = false

    fun fetchData() {
        viewModelScope.launch {
            val response = repository.getPokemonList(0,20)
            _pokemonList.postValue(response?.results)
        }
    }
    fun loadMoreData() {
        if (isLoading) return  // Prevent multiple simultaneous requests
        isLoading = true
        viewModelScope.launch {
            try {
                val response = repository.getPokemonList(currentOffset, pageSize)
                response?.let {
                    val currentList = response.results
                    _pokemonList.postValue(currentList)
                    currentOffset += pageSize
                }
            } catch (e: Exception) {

            } finally {
                isLoading = false
            }
        }
    }

}

