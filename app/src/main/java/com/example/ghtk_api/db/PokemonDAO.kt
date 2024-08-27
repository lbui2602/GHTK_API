package com.example.ghtk_api.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ghtk_api.models.Result

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemonList: List<Result>)

    @Query("SELECT * FROM pokemon_table")
    fun getAllPokemon(): List<Result>
}