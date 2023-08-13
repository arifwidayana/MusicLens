package com.arifwidayana.musiclens.data.network.service

import com.arifwidayana.musiclens.arch.base.BaseResponse
import com.arifwidayana.musiclens.data.network.model.response.MusicResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * service for hit API https://itunes.apple.com
 */
interface MusicService {
    /**
     * @GET method get data
     * @Quert annotation query to API
     * @param artist to search artist name by string
     */
    @GET("/search")
    fun searchArtistMusic(@Query("term") artist: String): Observable<BaseResponse<List<MusicResponse>>>
}