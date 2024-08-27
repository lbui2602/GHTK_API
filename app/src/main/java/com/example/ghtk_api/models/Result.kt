package com.example.ghtk_api.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "pokemon_table")
data class Result(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url") val url: String
) {
    fun getIdFromUrl(): Int? {
        return url.trimEnd('/').split("/").lastOrNull()?.toIntOrNull()
    }
}
