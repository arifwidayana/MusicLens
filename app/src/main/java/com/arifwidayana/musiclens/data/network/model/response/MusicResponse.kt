package com.arifwidayana.musiclens.data.network.model.response

import com.google.gson.annotations.SerializedName

data class MusicResponse(
    @SerializedName("artistName")
    val artistName: String?,
    @SerializedName("collectionName")
    val collectionName: String?,
    @SerializedName("trackName")
    val trackName: String?,
    @SerializedName("artworkUrl100")
    val artworkUrl100: String?,
    @SerializedName("previewUrl")
    val previewUrl: String?
)