package com.arifwidayana.musiclens.data.network.model.response

data class MusicParamResponse(
    val artistName: String,
    val collectionName: String,
    val trackName: String,
    val artworkUrl100: String,
    val previewUrl: String
)