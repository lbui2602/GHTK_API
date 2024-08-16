package com.example.ghtk_api.repository

import com.example.ghtk_api.api.ApiService
import com.example.ghtk_api.api.RetrofitClient
import com.example.ghtk_api.models.PokemonResponse

class PokemonRepository {
    private val apiService = RetrofitClient.create(ApiService::class.java)
    suspend fun getPokemonList(offset: Int, limit: Int): PokemonResponse? {
        return try {
            val response = apiService.getPokemonList(offset, limit)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}

