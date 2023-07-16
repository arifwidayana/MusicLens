package com.arifwidayana.musiclens.data.network.repository

import com.arifwidayana.musiclens.arch.base.BaseResponse
import com.arifwidayana.musiclens.arch.wrapper.DataResource
import com.arifwidayana.musiclens.data.network.datasource.MusicDatasource
import com.arifwidayana.musiclens.data.network.model.request.MusicRequest
import com.arifwidayana.musiclens.data.network.model.response.MusicResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias MusicDataResource = DataResource<BaseResponse<List<MusicResponse>>>
interface MusicRepository {
    suspend fun searchArtistMusic(musicRequest: MusicRequest): Flow<MusicDataResource>
}

/**
 * Collect data from datasource and combine with Flow and DataResource
 */
class MusicRepositoryImpl(
    private val musicDatasource: MusicDatasource
): MusicRepository, Repository() {
    override suspend fun searchArtistMusic(musicRequest: MusicRequest): Flow<MusicDataResource> = flow {
        emit(safeNetworkCall { musicDatasource.searchArtistMusic(musicRequest) })
    }
}