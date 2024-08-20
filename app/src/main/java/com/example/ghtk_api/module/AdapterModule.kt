package com.example.ghtk_api.module

import com.example.ghtk_api.adapter.PokemonAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object AdapterModule {

    @Provides
    fun providePokemonAdapter(): PokemonAdapter {
        return PokemonAdapter(mutableListOf())
    }
}
