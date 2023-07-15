package com.arifwidayana.musiclens.presentation.ui

import com.arifwidayana.musiclens.arch.base.BaseActivity
import com.arifwidayana.musiclens.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    ActivityMainBinding::inflate
) {
    override val viewModel: MainViewModel by inject()
    private val audioUrl = listOf(
        "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview125/v4/fd/bb/38/fdbb38d2-073d-4bc7-68c4-348a0be6d560/mzaf_4150435585996894188.plus.aac.p.m4a",
        "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-5.mp3",
        "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3"
        )
    private var audioPosition = 0

    override fun initView() {
        binding.apply {
            btnPrevious.setOnClickListener {
                if (audioPosition > 0) {
                    audioPosition--
                    playMusic()
                }
            }
            btnPlay.setOnClickListener {
                playMusic()
            }
            btnNext.setOnClickListener {
                if (audioPosition < audioUrl.size) {
                    audioPosition++
                    playMusic()
                }
            }
        }
    }

    override fun observeData() {
    }

    private fun playMusic() {
        viewModel.playAndPauseMusic(audioUrl[audioPosition])
    }
}