package com.arifwidayana.musiclens.presentation.ui

import android.widget.SeekBar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arifwidayana.musiclens.arch.utils.ext.source
import com.arifwidayana.musiclens.arch.utils.helper.playNextMusic
import com.arifwidayana.musiclens.arch.utils.helper.playOrPauseMusic
import com.arifwidayana.musiclens.arch.utils.helper.playPreviousMusic
import com.arifwidayana.musiclens.arch.utils.helper.progressSeekbar
import com.arifwidayana.musiclens.data.network.model.request.MusicRequest
import com.arifwidayana.musiclens.domain.SearchArtistUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val searchArtistUseCase: SearchArtistUseCase
) : MainContract, ViewModel() {
    private var searchJob: Job? = null
    private val _state = MutableStateFlow(MainState())
    override val state: StateFlow<MainState> = _state

    /**
     * init first searchArtist() function after apps started
     */
    init {
        searchArtist()
    }

    /**
     * to running every event in needed from main activity and will execute
     */
    override fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.SearchQueryChange -> {
                _state.update { it.copy(searchQuery = event.query) }
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(1500)
                    searchArtist()
                }
            }
        }
    }

    /**
     * @param artist will execute default param from
     * state value in MainState dataclass
     */
    private fun searchArtist(artist: String = state.value.searchQuery) {
        viewModelScope.launch {
            searchArtistUseCase(MusicRequest(artist = artist)).collect { vr ->
                vr.source(
                    doOnSuccess = { src ->
                        src.payload?.let { res ->
                            _state.update { it.copy(listMusic = res) }
                            state.value.listMusic
                        }
                    },
                    doOnError = { e ->
                        e.exception?.let { res ->
                            _state.update { it.copy(errorMessage = res) }
                        }
                    }
                )
            }
        }
    }

    /**
     * Play and pause music in one function
     * @param currentMusic getting position index from list music selected
     * @param seekBar running for seekbar progress sync with media player
     * @param progress value int to compare with current duration media player
     * @param fromUser is user interaction with seekbar
     */
    override fun playOrPause(
        currentMusic: Int,
        seekBar: SeekBar?,
        progress: Int,
        fromUser: Boolean
    ) {
        viewModelScope.launch {
            playOrPauseMusic(
                currentMusic = currentMusic,
                musicList = state.value.listMusic,
                seekBar = seekBar
            ) { isPlay ->
                _state.update { it.copy(iconButton = isPlay) }
            }
        }
    }

    /**
     * Next music after click button invoke current index music
     * @param currentMusic getting position index from list music selected
     * @param seekBar running for seekbar progress sync with media player
     */
    override fun nextMusic(currentMusic: Int, seekBar: SeekBar?) {
        viewModelScope.launch {
            playNextMusic(
                currentMusic = currentMusic,
                musicList = state.value.listMusic,
                seekBar = seekBar
            )
        }
    }

    /**
     * Previous music after click button invoke current index music
     * @param currentMusic getting position index from list music selected
     * @param seekBar running for seekbar progress sync with media player
     */
    override fun previousMusic(currentMusic: Int, seekBar: SeekBar?) {
        viewModelScope.launch {
            playPreviousMusic(
                currentMusic = currentMusic,
                musicList = state.value.listMusic,
                seekBar = seekBar
            )
        }
    }

    /**
     * Progress seekbar for listener changed after interaction from user
     * @param seekBar running for seekbar progress sync with media player
     * @param progress value int to compare with current duration media player
     * @param fromUser is user interaction with seekbar
     */
    override fun progressSeekbarMusic(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        viewModelScope.launch {
            progressSeekbar(seekBar, progress, fromUser)
        }
    }

    /**
     * when this ViewModel is no longer used and will be destroyed
     */
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
