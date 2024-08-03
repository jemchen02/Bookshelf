package com.example.bookshelf.data.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookSearchDto(
    val id: String,
    val bookVolumeInfoDto: BookVolumeInfoDto
)

@JsonClass(generateAdapter = true)
data class BookVolumeInfoDto(
    val title: String,
    val authors: List<String>?,
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
    val pageCount: Int?,
    val categories: List<String>?,
    val imageLinksDto: ImageLinksDto?
)