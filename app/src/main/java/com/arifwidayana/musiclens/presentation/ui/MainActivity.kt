package com.arifwidayana.musiclens.presentation.ui

import com.arifwidayana.musiclens.arch.base.BaseActivity
import com.arifwidayana.musiclens.arch.utils.ext.changed
import com.arifwidayana.musiclens.arch.utils.ext.source
import com.arifwidayana.musiclens.data.network.model.response.MusicParamResponse
import com.arifwidayana.musiclens.databinding.ActivityMainBinding
import com.arifwidayana.musiclens.domain.MusicViewParam
import com.arifwidayana.musiclens.presentation.adapter.MusicAdapter
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    ActivityMainBinding::inflate,
    MainViewModel::class.java
) {
    private var currentMusic = 0
    private var adapter: MusicAdapter? = null
    private lateinit var musicList: MusicViewParam
    override fun initView() {
        onView()
        onClick()
    }

    private fun onView() {
        binding.apply {
            svArtistMusic.changed(onQueryTextChange = {
                viewModel.onEvent(MainEvent.SearchQueryChange(it))
            })
        }
    }

    private fun onClick() {
        binding.apply {
            binding.sbMusic.changed(onProgressChanged = { seekbar, progress, fromUser ->
                viewModel.progressSeekbarMusic(seekbar, progress, fromUser)
            })
            btnPrevious.setOnClickListener {
                viewModel.previousMusic(
                    currentMusic = --currentMusic,
                    musicList = musicList,
                    seekBar = sbMusic
                )
                selectedMusic()
            }
            btnPlay.setOnClickListener {
                viewModel.playOrPause(
                    currentMusic = currentMusic,
                    musicList = musicList,
                    seekBar = sbMusic
                )
                selectedMusic()
            }
            btnNext.setOnClickListener {
                viewModel.nextMusic(
                    currentMusic = ++currentMusic,
                    musicList = musicList,
                    seekBar = sbMusic
                )
                selectedMusic()
            }
        }
    }

    /**
     * observe data from view model using lifecycle
     */
    override fun observeData() {
        viewModel.stateMusic.observe(this) {
            it.source(
                doOnSuccess = { src ->
                    src.payload?.let { res ->
                        musicList = res
                        setMusicAdapter(res)
                    }
                },
                doOnError = { e ->
                    Timber.tag("E").e(e.exception)
                }
            )
        }
        viewModel.state.observe(this) {
//            setIconButton(it.iconButton)
        }
    }

    /**
     * set icon button play and pause
     */
    private fun setIconButton(setIcon: Int) {
        binding.btnPlay.setImageResource(setIcon)
    }

    /**
     * set data to adapter using submit list and invoke click using lambda in adapter
     */
    private fun setMusicAdapter(listMusic: List<MusicParamResponse>) {
        adapter = MusicAdapter {
            currentMusic = it
            viewModel.playOrPause(
                currentMusic = currentMusic,
                musicList = musicList,
                seekBar = binding.sbMusic
            )
            selectedMusic()
        }
        binding.rvMusic.adapter = adapter
        adapter?.submitList(listMusic)
    }

    /**
     * every click item music in adapter will be update the music
     */
    private fun selectedMusic() = adapter?.selectMusic(currentMusic)
}