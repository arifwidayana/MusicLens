package com.arifwidayana.musiclens.data.network.repository

import com.arifwidayana.musiclens.arch.base.BaseResponse
import com.arifwidayana.musiclens.arch.wrapper.DataResource
import com.arifwidayana.musiclens.data.network.datasource.MusicDatasource
import com.arifwidayana.musiclens.data.network.model.request.MusicRequest
import com.arifwidayana.musiclens.data.network.model.response.MusicResponse
import io.reactivex.rxjava3.core.Observable

typealias MusicDataResource = DataResource<BaseResponse<List<MusicResponse>>>
interface MusicRepository {
    fun searchArtistMusic(musicRequest: MusicRequest): Observable<MusicDataResource>
}

/**
 * Collect data from datasource and combine with Flow and DataResource
 */
class MusicRepositoryImpl(
    private val musicDatasource: MusicDatasource
): MusicRepository, Repository() {
    override fun searchArtistMusic(musicRequest: MusicRequest): Observable<MusicDataResource> = Observable.defer {
        safeNetworkCall { musicDatasource.searchArtistMusic(musicRequest) }
    }
}