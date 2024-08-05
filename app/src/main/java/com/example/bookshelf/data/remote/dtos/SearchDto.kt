package com.example.bookshelf.data.remote.dtos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResultDto(
    val items: List<SearchItemDto>
)

@JsonClass(generateAdapter = true)
data class SearchItemDto(
    val id: String,
    val volumeInfo: VolumeInfoDto
)

@JsonClass(generateAdapter = true)
data class VolumeInfoDto(
    val title: String?,
    val imageLinks: ImageLinksDto?
)

@JsonClass(generateAdapter = true)
data class ImageLinksDto(
    val smallThumbnail: String?,
    val thumbnail: String?
)