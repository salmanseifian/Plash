package com.salmanseifian.plash.room.models

import com.salmanseifian.plash.network.response.Tag
import com.salmanseifian.plash.network.response.Urls

data class DbSinglePhoto(
    val id: String?,
    val created_at: String?,
    val updated_at: String?,
    val width: Int?,
    val height: Int?,
    val color: String?,
    val downloads: Int?,
    val likes: Int?,
    val liked_by_user: Boolean?,
    val description: String?,
    val tags: List<Tag>?,
    val urls: Urls?
)