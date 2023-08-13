package com.arifwidayana.musiclens.domain

import com.arifwidayana.musiclens.arch.base.BaseUseCase
import com.arifwidayana.musiclens.arch.wrapper.ViewResource
import com.arifwidayana.musiclens.data.network.model.mapper.MusicListMapper
import com.arifwidayana.musiclens.data.network.model.request.MusicRequest
import com.arifwidayana.musiclens.data.network.model.response.MusicParamResponse
import com.arifwidayana.musiclens.data.network.repository.MusicRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

typealias MusicViewParam = List<MusicParamResponse>

/**
 * specific cases for search artist and handle business logic
 */
class SearchArtistUseCase @Inject constructor(
    private val musicRepository: MusicRepository,
    scheduler: Scheduler
) : BaseUseCase<MusicRequest, MusicViewParam>(scheduler) {
    override fun execute(param: MusicRequest?): Observable<ViewResource<MusicViewParam>> = Observable.defer {
        musicRepository.searchArtistMusic(param ?: MusicRequest(""))
            .map { res ->
                ViewResource.Success(MusicListMapper.toViewParam(res.payload?.data))
            }
    }
}