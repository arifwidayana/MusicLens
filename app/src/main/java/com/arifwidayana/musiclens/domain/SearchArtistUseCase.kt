package com.arifwidayana.musiclens.domain

import com.arifwidayana.musiclens.arch.base.BaseUseCase
import com.arifwidayana.musiclens.arch.utils.ext.suspendSource
import com.arifwidayana.musiclens.arch.wrapper.ViewResource
import com.arifwidayana.musiclens.data.network.model.mapper.MusicListMapper
import com.arifwidayana.musiclens.data.network.model.request.MusicRequest
import com.arifwidayana.musiclens.data.network.model.response.MusicParamResponse
import com.arifwidayana.musiclens.data.network.repository.MusicRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias MusicViewParam = List<MusicParamResponse>

/**
 * specific cases for search artist and handle business logic
 */
class SearchArtistUseCase(
    private val musicRepository: MusicRepository,
    coroutineDispatcher: CoroutineDispatcher
) : BaseUseCase<MusicRequest, MusicViewParam>(coroutineDispatcher) {
    override suspend fun execute(param: MusicRequest?): Flow<ViewResource<MusicViewParam>> = flow {
        param?.let {
            emit(ViewResource.Loading())
            musicRepository.searchArtistMusic(it).collect { res ->
                res.suspendSource(
                    doOnSuccess = { source ->
                        emit(ViewResource.Success(MusicListMapper.toViewParam(source.payload?.data)))
                    },
                    doOnError = { error ->
                        emit(ViewResource.Error(error.exception))
                    }
                )
            }
        }
    }
}