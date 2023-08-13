package com.arifwidayana.musiclens.presentation.ui

import android.widget.SeekBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arifwidayana.musiclens.arch.base.BaseViewModel
import com.arifwidayana.musiclens.arch.utils.helper.playNextMusic
import com.arifwidayana.musiclens.arch.utils.helper.playOrPauseMusic
import com.arifwidayana.musiclens.arch.utils.helper.playPreviousMusic
import com.arifwidayana.musiclens.arch.utils.helper.progressSeekbar
import com.arifwidayana.musiclens.arch.wrapper.ViewResource
import com.arifwidayana.musiclens.data.network.model.request.MusicRequest
import com.arifwidayana.musiclens.domain.MusicViewParam
import com.arifwidayana.musiclens.domain.SearchArtistUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val searchArtistUseCase: SearchArtistUseCase
) : MainContract, BaseViewModel() {
    private val subject = PublishSubject.create<String>()
    private val _state: MutableLiveData<MainState> = MutableLiveData(MainState())
    override val state: LiveData<MainState> = _state
    private val _stateMusic: MutableLiveData<ViewResource<MusicViewParam>> = MutableLiveData()
    val stateMusic: LiveData<ViewResource<MusicViewParam>> = _stateMusic

    /**
     * init first searchArtist() function after apps started
     */
    init {
        searchArtist()
        addDisposable(
            subject.debounce(1500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    searchArtist(it)
                }
        )
    }

    /**
     * to running every event in needed from main activity and will execute
     */
    override fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.SearchQueryChange -> {
                _state.value = _state.value?.copy(searchQuery = event.query)
                subject.onNext(event.query)
            }
        }
    }

    /**
     * @param artist will execute default param from
     * state value in MainState dataclass
     */
    private fun searchArtist(artist: String? = state.value?.searchQuery) {
        addDisposable(
            searchArtistUseCase(MusicRequest(artist = artist.toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { src ->
                    _stateMusic.postValue(src)
                    _state.value = src.payload?.let { _state.value?.copy(listMusic = it) }
                }
        )
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
        musicList: MusicViewParam,
        seekBar: SeekBar?,
        progress: Int,
        fromUser: Boolean
    ) {
        playOrPauseMusic(
            currentMusic = currentMusic,
            musicList = musicList,
            seekBar = seekBar
        ) { isPlay ->
            _state.value = _state.value?.copy(iconButton = isPlay)
        }
    }

    /**
     * Next music after click button invoke current index music
     * @param currentMusic getting position index from list music selected
     * @param seekBar running for seekbar progress sync with media player
     */
    override fun nextMusic(currentMusic: Int, musicList: MusicViewParam, seekBar: SeekBar?) {
        playNextMusic(
            currentMusic = currentMusic,
            musicList = musicList,
            seekBar = seekBar
        )
    }

    /**
     * Previous music after click button invoke current index music
     * @param currentMusic getting position index from list music selected
     * @param seekBar running for seekbar progress sync with media player
     */
    override fun previousMusic(currentMusic: Int, musicList: MusicViewParam, seekBar: SeekBar?) {
        playPreviousMusic(
            currentMusic = currentMusic,
            musicList = musicList,
            seekBar = seekBar
        )
    }

    /**
     * Progress seekbar for listener changed after interaction from user
     * @param seekBar running for seekbar progress sync with media player
     * @param progress value int to compare with current duration media player
     * @param fromUser is user interaction with seekbar
     */
    override fun progressSeekbarMusic(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        progressSeekbar(seekBar, progress, fromUser)
    }
}
