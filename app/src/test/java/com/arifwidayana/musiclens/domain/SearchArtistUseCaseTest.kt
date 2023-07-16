package com.arifwidayana.musiclens.domain

import com.arifwidayana.musiclens.arch.wrapper.ViewResource
import com.arifwidayana.musiclens.data.network.model.response.MusicParamResponse
import com.arifwidayana.musiclens.data.network.model.response.MusicResponse
import com.arifwidayana.musiclens.data.network.repository.MusicRepository
import com.arifwidayana.musiclens.data.network.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


/*
* unfinished
*/
@RunWith(MockitoJUnitRunner::class)
class SearchArtistUseCaseTest : Repository() {

    @Mock
    private lateinit var musicRepository: MusicRepository
    private lateinit var searchArtistUseCase: SearchArtistUseCase

    @Before
    fun setup() {
        searchArtistUseCase = SearchArtistUseCase(musicRepository, Dispatchers.IO)
    }

    @Test
    fun `verify correct user search artist`() {
//        `when`(searchArtistUseCase(MusicRequest(any()))).thenReturn(data)
    }

    companion object {
        private val list1 = MusicResponse(
            artistName = "Tulus",
            collectionName = "Gajah",
            trackName = "Gajah",
            artworkUrl100 = "www.music-image-tulus.jpg",
            previewUrl = "www.music-tulus.mp4"
        )
        private val data = flow {
            emit(ViewResource.Loading())
            emit(ViewResource.Success(artistMusicParam()))
            emit(ViewResource.Error(IllegalArgumentException("error")))
        }
        private val listParam1 = MusicParamResponse(
            artistName = "Tulus",
            collectionName = "Gajah",
            trackName = "Gajah",
            artworkUrl100 = "www.music-image-tulus.jpg",
            previewUrl = "www.music-tulus.mp4"
        )
        private val listParam2 = MusicParamResponse(
            artistName = "Post Malone",
            collectionName = "Sunflower",
            trackName = "Sunflower",
            artworkUrl100 = "www.music-image-post-malone.jpg",
            previewUrl = "www.music-post-malone.mp4"
        )

        private fun artistMusicParam() = listOf(listParam1, listParam2)
    }
}