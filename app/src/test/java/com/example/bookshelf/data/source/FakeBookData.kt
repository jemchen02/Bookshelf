package com.example.bookshelf.data.source

import com.example.bookshelf.data.remote.mappers.toBookPreviewList
import com.example.bookshelf.data.remote.response.BookSearchDto
import com.example.bookshelf.data.remote.response.BookVolumeInfoDto
import com.example.bookshelf.data.remote.response.SearchItemDto
import com.example.bookshelf.data.remote.response.SearchResultDto
import com.example.bookshelf.data.remote.response.VolumeInfoDto
import com.example.bookshelf.util.bookSearchDto
import com.example.bookshelf.util.searchItemDto

object FakeBookData {
    val bookItem = bookSearchDto("aaa", "Three As")
    val bookList = listOf(
        searchItemDto("111", "Triple ones"),
        searchItemDto("222", "Triple twos"),
        searchItemDto("abc", "Alphabet"),
    )
    val bookPreviews = SearchResultDto(bookList).toBookPreviewList()
}