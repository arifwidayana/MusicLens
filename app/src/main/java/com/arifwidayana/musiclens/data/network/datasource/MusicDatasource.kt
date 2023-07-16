package com.arifwidayana.musiclens.data.network.datasource

import com.arifwidayana.musiclens.arch.base.BaseResponse
import com.arifwidayana.musiclens.data.network.model.request.MusicRequest
import com.arifwidayana.musiclens.data.network.model.response.MusicResponse
import com.arifwidayana.musiclens.data.network.service.MusicService

interface MusicDatasource {
    suspend fun searchArtistMusic(musicRequest: MusicRequest): BaseResponse<List<MusicResponse>>
}

/**
 * @param MusicService get data artist and collect from network service
 * implement MusicDatasource function
 */
class MusicDatasourceImpl(
    private val musicService: MusicService
) : MusicDatasource {
    override suspend fun searchArtistMusic(musicRequest: MusicRequest): BaseResponse<List<MusicResponse>> {
        return musicService.searchArtistMusic(musicRequest.artist)
    }
}