package com.example.ghtk_api.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ghtk_api.databinding.PokemonItemBinding
import com.example.ghtk_api.models.Result
import kotlin.random.Random

class PokemonAdapter(
    private var pokemonList: MutableList<Result>
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

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
        notifyItemRangeInserted(pokemonList.size - newPokemons.size, newPokemons.size)
    }
    fun updateList(newPokemons: List<Result>) {
        pokemonList = newPokemons.toMutableList()
        notifyDataSetChanged()
    }

    inner class PokemonViewHolder(private val binding: PokemonItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Result) {
            binding.tvName.text = pokemon.name
            binding.tvId.text = "#00${pokemon.getIdFromUrl()}"
            binding.llItem.setBackgroundColor(getRandomColor())
            val id = pokemon.getIdFromUrl()
            Glide.with(binding.imgPokemon.context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png")
                .into(binding.imgPokemon)
            binding.executePendingBindings()
        }
    }

    private fun getRandomColor(): Int {
        val random = Random.nextInt(0xFFFFFF + 1)
        return Color.rgb((random shr 16) and 0xFF, (random shr 8) and 0xFF, random and 0xFF)
    }
}
