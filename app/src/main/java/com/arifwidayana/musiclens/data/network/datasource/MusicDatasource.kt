package com.arifwidayana.musiclens.data.network.datasource

import com.arifwidayana.musiclens.arch.base.BaseResponse
import com.arifwidayana.musiclens.data.network.model.request.MusicRequest
import com.arifwidayana.musiclens.data.network.model.response.MusicResponse
import com.arifwidayana.musiclens.data.network.service.MusicService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface MusicDatasource {
    fun searchArtistMusic(musicRequest: MusicRequest): Observable<BaseResponse<List<MusicResponse>>>
}

/**
 * @param MusicService get data artist and collect from network service
 * implement MusicDatasource function
 */
class MusicDatasourceImpl @Inject constructor(
    private val musicService: MusicService
) : MusicDatasource {
    override fun searchArtistMusic(musicRequest: MusicRequest): Observable<BaseResponse<List<MusicResponse>>> {
        return musicService.searchArtistMusic(musicRequest.artist)
    }
}