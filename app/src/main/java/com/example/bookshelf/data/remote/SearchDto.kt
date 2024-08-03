package com.example.bookshelf.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class SearchResultDto(
    val items: List<SearchItemDto> = emptyList()
)

@Serializable
data class SearchItemDto(
    val id: String,
    val volumeInfo: VolumeInfoDto
)

@Serializable
data class VolumeInfoDto(
    val title: String,
    val authors: List<String> = emptyList(),
    val publishedDate: String = "",
    val description: String = "",
    val categories: List<String> = emptyList(),
    val imageLinks: ImageLinksDto = ImageLinksDto()
)

@Serializable
data class ImageLinksDto(
    val smallThumbnail: String = "",
    val thumbnail: String = ""
)