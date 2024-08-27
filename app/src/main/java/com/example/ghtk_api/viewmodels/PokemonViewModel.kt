package com.example.ghtk_api.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghtk_api.NetworkStatusLiveData
import com.example.ghtk_api.repository.PokemonRepository
import com.example.ghtk_api.models.PokemonResponse
import com.example.ghtk_api.models.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    application: Application,
    private val repository: PokemonRepository
) : ViewModel() {

    private val _pokemonList = MutableLiveData<List<Result>?>()
    val pokemonList: MutableLiveData<List<Result>?> get() = _pokemonList

    private val _isConnected = NetworkStatusLiveData(application)
    val isConnected: LiveData<Boolean> get() = _isConnected

    private var currentOffset = 20
    private val pageSize = 20
    private var isLoading = false

    init {
        _isConnected.checkInitialNetworkStatus()
    }

    fun fetchData() {
        viewModelScope.launch {
            val response = repository.getPokemonList(0, 20)
            _pokemonList.postValue(response?.results)
        }
    }

    fun loadMoreData() {
        if (isLoading) return
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
