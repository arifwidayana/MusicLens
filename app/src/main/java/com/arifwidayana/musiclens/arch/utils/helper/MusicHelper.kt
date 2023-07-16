package com.arifwidayana.musiclens.arch.utils.helper

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import com.arifwidayana.musiclens.R
import com.arifwidayana.musiclens.arch.utils.constant.Constant.DEFAULT_TIME
import com.arifwidayana.musiclens.data.network.model.response.MusicParamResponse
import timber.log.Timber

private val mediaPlayer = MediaPlayer()
private val handler = Handler(Looper.getMainLooper())
private var currentMusicIndex = 0
private var pauseLength = 0
private var tempUri: String = ""
private var lastTime = DEFAULT_TIME

fun playOrPauseMusic(
    currentMusic: Int,
    musicList: List<MusicParamResponse>,
    seekBar: SeekBar? = null,
    isPlay: ((Int) -> Unit)? = null
) {
    val url = musicList[currentMusic].previewUrl
    try {
        if (tempUri != url) {
            currentMusicIndex = currentMusic
            tempUri = url
            mediaPlayer.apply {
                reset()
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(tempUri)
                prepare() // might take long! (for buffering, etc)
                start()
                isPlay?.invoke(setIcon())
            }
            Timber.d("playOrPauseMusic: " + musicList[currentMusic].trackName)
        } else {
            mediaPlayer.apply {
                if (isPlaying) {
                    pause()
                    isPlay?.invoke(setIcon())
                    pauseLength = currentPosition
                } else {
                    seekTo(pauseLength)
                    start()
                    isPlay?.invoke(setIcon())
                }
            }
        }
        updateSeekbarProgress(seekBar)
        mediaPlayer.setOnCompletionListener {
            isPlay?.invoke(R.drawable.ic_play_button)
            pauseLength = 0
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Timber.e("playOrPauseMusic: $e")
    }
}

fun playNextMusic(currentMusic: Int, musicList: List<MusicParamResponse>, seekBar: SeekBar?) {
    playOrPauseMusic(currentMusic, musicList, seekBar)
    pauseLength = 0
}

fun playPreviousMusic(currentMusic: Int, musicList: List<MusicParamResponse>, seekBar: SeekBar?) {
    playOrPauseMusic(currentMusic, musicList, seekBar)
    pauseLength = 0
}

fun progressSeekbar(seekBar: SeekBar? = null, progress: Int = 0, fromUser: Boolean = false) {
    seekBar?.max = mediaPlayer.duration
    if (fromUser) {
        mediaPlayer.seekTo(progress)
        pauseLength = progress
    }
    updateSeekbarProgress(seekBar)
}

fun updateSeekbarProgress(seekBar: SeekBar?) {
    val updateSeekBarRunnable = object : Runnable {
        override fun run() {
            when {
                mediaPlayer.isPlaying && System.currentTimeMillis() - lastTime >= DEFAULT_TIME -> {
                    seekBar?.progress = mediaPlayer.currentPosition
                    lastTime = System.currentTimeMillis()
                }

                !mediaPlayer.isPlaying && mediaPlayer.currentPosition == mediaPlayer.duration -> {
                    seekBar?.progress = mediaPlayer.duration
                    pauseLength = 0
                }
            }
            handler.postDelayed(this, 1000) // Update every second (1000 milliseconds)
        }
    }
    handler.post(updateSeekBarRunnable)
}

private fun setIcon() =
    if (!mediaPlayer.isPlaying) R.drawable.ic_play_button else R.drawable.ic_pause