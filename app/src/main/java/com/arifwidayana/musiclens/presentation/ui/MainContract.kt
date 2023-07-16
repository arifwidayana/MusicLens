package com.arifwidayana.musiclens.presentation.ui

import android.widget.SeekBar
import kotlinx.coroutines.flow.StateFlow

interface MainContract {
    val state: StateFlow<MainState>
    fun onEvent(event: MainEvent)
    fun playOrPause(currentMusic: Int, seekBar: SeekBar? = null, progress: Int = 0, fromUser: Boolean = false)
    fun nextMusic(currentMusic: Int, seekBar: SeekBar? = null)
    fun previousMusic(currentMusic: Int, seekBar: SeekBar? = null)
    fun progressSeekbarMusic(seekBar: SeekBar? = null, progress: Int = 0, fromUser: Boolean = false)
}