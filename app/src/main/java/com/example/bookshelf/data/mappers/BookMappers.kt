package com.example.bookshelf.data.mappers

import com.example.bookshelf.data.remote.BookSearchDto
import com.example.bookshelf.data.remote.SearchResultDto
import com.example.bookshelf.domain.model.Book
import com.example.bookshelf.domain.model.BookPreview

fun SearchResultDto.toBookPreviewList(): List<BookPreview> {
    return items.map { book->
        BookPreview(
            book.id,
            book.volumeInfo.title ?: "",
            book.volumeInfo.imageLinks?.smallThumbnail
        )
    }
}

fun BookSearchDto.toBook(): Book {
    return Book(
        id = id,
        title = bookVolumeInfoDto.title,
        authors = bookVolumeInfoDto.authors ?: emptyList(),
        publisher = bookVolumeInfoDto.publisher ?: "",
        publishedDate = bookVolumeInfoDto.publishedDate ?: "",
        description = bookVolumeInfoDto.description ?: "",
        pageCount = bookVolumeInfoDto.pageCount ?: 0,
        categories = bookVolumeInfoDto.categories ?: emptyList(),
        thumbnail = bookVolumeInfoDto.imageLinksDto?.smallThumbnail ?: ""
    )
}