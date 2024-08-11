package com.example.bookshelf.util

import com.example.bookshelf.data.remote.response.BookSearchDto
import com.example.bookshelf.data.remote.response.BookVolumeInfoDto
import com.example.bookshelf.data.remote.response.SearchItemDto
import com.example.bookshelf.data.remote.response.VolumeInfoDto
import com.example.bookshelf.data.source.FakeBookData
import com.example.bookshelf.presentation.ui.screens.search.SearchState

fun bookSearchDto(id: String, title: String) = BookSearchDto(
    id = id,
    volumeInfo = BookVolumeInfoDto(
        title, null, null, null, null, null, null, null
    )
)
fun searchItemDto(id: String, title: String) = SearchItemDto(
    id = id,
    volumeInfo = VolumeInfoDto(
        title,
        null
    )
)