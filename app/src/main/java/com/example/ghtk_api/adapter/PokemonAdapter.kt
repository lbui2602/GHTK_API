package com.example.ghtk_api.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ghtk_api.R
import com.example.ghtk_api.databinding.PokemonItemBinding
import com.example.ghtk_api.models.Result
import kotlin.random.Random

class PokemonAdapter(private val pokemonList: MutableList<Result>) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }

    override fun getItemCount(): Int = pokemonList.size

    fun addPokemons(newPokemons: List<Result>) {
        pokemonList.addAll(newPokemons)
        notifyItemRangeInserted(pokemonList.size,20)
    }

    inner class PokemonViewHolder(private val binding: PokemonItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind(pokemon: Result) {
            binding.tvName.text = pokemon.name
            binding.tvId.text = "#00"+pokemon.getIdFromUrl()
            binding.llItem.setBackgroundColor(getRandomColor())
            val id = pokemon.getIdFromUrl()
            Glide.with(binding.root.context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png")
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imgPokemon)
            binding.executePendingBindings()
        }
    }
    private fun getRandomColor(): Int {
        val random = Random.nextInt(0xFFFFFF + 1)
        return Color.rgb((random shr 16) and 0xFF, (random shr 8) and 0xFF, random and 0xFF)
    }
}
