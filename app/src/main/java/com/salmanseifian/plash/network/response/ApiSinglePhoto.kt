package com.salmanseifian.plash.network.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiSinglePhoto(
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
    val exif: Exif?,
    val location: Location?,
    val tags: List<Tag>?,
    val current_user_collections: List<CurrentUserCollection>?,
    val urls: Urls?,
    val links: Links,
    val user: User?
)

@JsonClass(generateAdapter = true)
data class Exif(
    val make: String?,
    val model: String?,
    val exposure_time: String?,
    val aperture: String?,
    val focal_length: String?,
    val iso: Int?
)

@JsonClass(generateAdapter = true)
data class Location(
    val city: String?,
    val country: String?,
    val position: Position?
)

@JsonClass(generateAdapter = true)
data class Position(
    val latitude: Double?,
    val longitude: Double?
)

@JsonClass(generateAdapter = true)
data class Tag(
    val title: String?
)

@JsonClass(generateAdapter = true)
data class CurrentUserCollection(
    val id: Int?,
    val title: String?,
    val published_at: String?,
    val updated_at: String?,
    val cover_photo: Any?,
    val user: Any?
)