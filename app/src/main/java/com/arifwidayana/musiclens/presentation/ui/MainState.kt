package com.arifwidayana.musiclens.presentation.ui

import com.arifwidayana.musiclens.R
import com.arifwidayana.musiclens.domain.MusicViewParam

/**
 * default state using between view model and activity
 */
data class MainState(
    val listMusic: MusicViewParam = emptyList(),
    var iconButton: Int = R.drawable.ic_play_button,
    var searchQuery: String = "rhapsody bohemian",
    val errorMessage: Exception = Exception()
)
