package com.arifwidayana.musiclens.arch.utils.ext

import android.widget.SearchView
import android.widget.SeekBar

fun SearchView.changed(
    onQueryTextSubmit: ((String) -> Unit)? = null,
    onQueryTextChange: ((String) -> Unit)? = null
) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            onQueryTextSubmit?.invoke(query.toString())
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            onQueryTextChange?.invoke(newText.toString())
            return false
        }
    })
}

fun SeekBar.changed(
    onProgressChanged: ((SeekBar?, Int, Boolean) -> Unit)? = null,
    onStartTrackingTouch: ((SeekBar?) -> Unit)? = null,
    onStopTrackingTouch: ((SeekBar?) -> Unit)? = null
) {
    this.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            onProgressChanged?.invoke(seekBar, progress, fromUser)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            onStartTrackingTouch?.invoke(seekBar)
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            onStopTrackingTouch?.invoke(seekBar)
        }
    })
}