package com.example.bookshelf.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookSearchDto(
    val id: String,
    val selfLink: String,
    val volumeInfo: BookVolumeInfoDto
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
    val imageLinks: ImageLinksDto?
)