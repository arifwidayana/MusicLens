package com.arifwidayana.musiclens.data.network.repository

import com.arifwidayana.musiclens.arch.base.BaseRepository
import com.arifwidayana.musiclens.arch.base.BaseResponse
import com.google.gson.Gson
import com.google.gson.JsonParseException
import okhttp3.ResponseBody
import org.koin.java.KoinJavaComponent

/**
 * Repository child of BaseRepository using for
 * catching the error and throw error message
 */
open class Repository : BaseRepository() {
    private val gson: Gson by KoinJavaComponent.inject(Gson::class.java)

    override fun <T> getErrorMessageFromApi(response: T): String {
        val responseBody = response as ResponseBody
        return try {
            val body = gson.fromJson(responseBody.string(), BaseResponse::class.java)
            body.message ?: "Error Api"
        } catch (e: JsonParseException) {
            "Error Api"
        }
    }
}