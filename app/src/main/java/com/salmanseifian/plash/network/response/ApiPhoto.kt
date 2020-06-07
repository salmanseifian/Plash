package com.salmanseifian.plash.network.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiPhoto(
    val id: String?,
    val created_at: String?,
    val updated_at: String?,
    val width: Int,
    val height: Int?,
    val color: String?,
    val likes: Int?,
    val liked_by_user: Boolean?,
    val description: String?,
    val user: User,
    val current_user_collections: List<Any>,
    val urls: Urls,
    val links: Links
)

@JsonClass(generateAdapter = true)
data class Links(
    val self: String,
    val html: String,
    val download: String,
    val download_location: String
)

@JsonClass(generateAdapter = true)
data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)

@JsonClass(generateAdapter = true)
data class User(
    val bio: String?,
    val id: String?,
    val instagram_username: String?,
    val links: LinksX,
    val location: String?,
    val name: String?,
    val portfolio_url: String?,
    val profile_image: ProfileImage,
    val total_collections: Int?,
    val total_likes: Int?,
    val total_photos: Int?,
    val twitter_username: String?,
    val username: String?
)

@JsonClass(generateAdapter = true)
data class LinksX(
    val html: String?,
    val likes: String?,
    val photos: String?,
    val portfolio: String?,
    val self: String?
)

@JsonClass(generateAdapter = true)
data class ProfileImage(
    val large: String?,
    val medium: String?,
    val small: String?
)