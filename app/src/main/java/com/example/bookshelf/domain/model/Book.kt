package com.example.bookshelf.domain.model

import com.example.bookshelf.data.remote.ImageLinksDto

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val publisher: String,
    val publishedDate: String,
    val description: String,
    val pageCount: Int,
    val categories: List<String>,
    val thumbnail: String
)