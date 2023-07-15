package com.arifwidayana.musiclens.arch.base

import com.google.gson.annotations.SerializedName

/**
 * BaseResponse created as the first step when hit API and return data can be modify
 * and used according to different dataclass by Generic
 */
data class BaseResponse<D>(
    @SerializedName("name")
    val name: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("resultCount")
    val resultCount: Int?,
    @SerializedName("results")
    val data: D?
)
