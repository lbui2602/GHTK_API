package com.example.ghtk_api.models


import com.google.gson.annotations.SerializedName

class Result(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
){
    fun getIdFromUrl(): Int? {
        return url.trimEnd('/').split("/").lastOrNull()?.toIntOrNull()
    }
}