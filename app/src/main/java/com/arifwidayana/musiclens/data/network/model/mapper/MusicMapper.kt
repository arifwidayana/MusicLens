package com.arifwidayana.musiclens.data.network.model.mapper

import com.arifwidayana.musiclens.arch.mapper.ListMapper
import com.arifwidayana.musiclens.arch.mapper.ViewParamMapper
import com.arifwidayana.musiclens.data.network.model.response.MusicParamResponse
import com.arifwidayana.musiclens.data.network.model.response.MusicResponse

/**
 * MusicMapper will transform data resource to be view param without null
 */
object MusicMapper : ViewParamMapper<MusicResponse, MusicParamResponse> {
    override fun toViewParam(dataObject: MusicResponse?): MusicParamResponse =
        MusicParamResponse(
            artistName = dataObject?.artistName ?: "Empty name",
            collectionName = dataObject?.collectionName ?: "Empty album",
            trackName = dataObject?.trackName ?: "Empty track",
            artworkUrl100 = dataObject?.artworkUrl100 ?: "Empty picture",
            previewUrl = dataObject?.previewUrl ?: "Empty preview"
        )
}

object MusicListMapper : ViewParamMapper<List<MusicResponse>, List<MusicParamResponse>> {
    override fun toViewParam(dataObject: List<MusicResponse>?): List<MusicParamResponse> =
        ListMapper(MusicMapper).toViewParams(dataObject)
}
