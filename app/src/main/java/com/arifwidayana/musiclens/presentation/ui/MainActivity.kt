package com.arifwidayana.musiclens.presentation.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.arifwidayana.musiclens.arch.base.BaseActivity
import com.arifwidayana.musiclens.arch.utils.ext.changed
import com.arifwidayana.musiclens.data.network.model.response.MusicParamResponse
import com.arifwidayana.musiclens.databinding.ActivityMainBinding
import com.arifwidayana.musiclens.presentation.adapter.MusicAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    ActivityMainBinding::inflate
) {
    override val viewModel: MainViewModel by inject()
    private var currentMusic = 0
    private var adapter: MusicAdapter? = null
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
                    seekBar = sbMusic
                )
                selectedMusic()
            }
            btnPlay.setOnClickListener {
                viewModel.playOrPause(
                    currentMusic = currentMusic,
                    seekBar = sbMusic
                )
                selectedMusic()
            }
            btnNext.setOnClickListener {
                viewModel.nextMusic(
                    currentMusic = ++currentMusic,
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect {
                    setMusicAdapter(it.listMusic)
                    setIconButton(it.iconButton)
                    Timber.tag("E").e(it.errorMessage)
                }
            }
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