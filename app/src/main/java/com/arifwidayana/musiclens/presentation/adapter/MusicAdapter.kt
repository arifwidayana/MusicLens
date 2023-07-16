package com.arifwidayana.musiclens.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.arifwidayana.musiclens.data.network.model.response.MusicParamResponse
import com.arifwidayana.musiclens.databinding.CardMusicBinding

class MusicAdapter(
    private val onClick: (Int) -> Unit
) : ListAdapter<MusicParamResponse, MusicAdapter.MusicSearchHolder>(
    Differ()
) {
    private var currentSelected: Int = RecyclerView.NO_POSITION

    class MusicSearchHolder(
        private val binding: CardMusicBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        /**
         * bind holder for each item will reproduce according data list
         */
        fun bind(currentMusic: MusicParamResponse, currentPosition: Int, isSelected: Boolean) {
            binding.apply {
                sivMusic.load(currentMusic.artworkUrl100)
                tvSongName.text = currentMusic.trackName
                tvArtistName.text = currentMusic.artistName
                tvAlbumName.text = currentMusic.collectionName
                sivPlayingSong.visibility = if (isSelected) View.VISIBLE else View.GONE

                root.setOnClickListener {
                    onClick.invoke(currentPosition)
                }
            }
        }
    }

    class Differ : DiffUtil.ItemCallback<MusicParamResponse>() {
        override fun areItemsTheSame(
            oldItem: MusicParamResponse,
            newItem: MusicParamResponse
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: MusicParamResponse,
            newItem: MusicParamResponse
        ): Boolean {
            return oldItem.artistName == newItem.artistName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicSearchHolder {
        val binding = CardMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicSearchHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: MusicSearchHolder, position: Int) {
        holder.bind(getItem(position), position, currentSelected == position)
    }

    /**
     * update position selected item and updated data in adapter after changed
     */
    @SuppressLint("NotifyDataSetChanged")
    fun selectMusic(position: Int) {
        currentSelected = position
        notifyDataSetChanged()
    }
}