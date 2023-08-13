package com.arifwidayana.musiclens.presentation.ui

import android.widget.SeekBar
import androidx.lifecycle.LiveData
import com.arifwidayana.musiclens.domain.MusicViewParam

interface MainContract {
    val state: LiveData<MainState>
    fun onEvent(event: MainEvent)
    fun playOrPause(currentMusic: Int, musicList: MusicViewParam, seekBar: SeekBar? = null, progress: Int = 0, fromUser: Boolean = false)
    fun nextMusic(currentMusic: Int, musicList: MusicViewParam, seekBar: SeekBar? = null)
    fun previousMusic(currentMusic: Int, musicList: MusicViewParam, seekBar: SeekBar? = null)
    fun progressSeekbarMusic(seekBar: SeekBar? = null, progress: Int = 0, fromUser: Boolean = false)
}