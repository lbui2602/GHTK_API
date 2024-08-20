package com.example.ghtk_api.repository

import com.example.ghtk_api.api.ApiService
import com.example.ghtk_api.models.PokemonResponse
import com.example.ghtk_api.module.NetworkModule
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val apiService: ApiService
) {
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
