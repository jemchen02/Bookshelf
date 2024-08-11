package com.example.bookshelf.data.remote.mappers

import com.example.bookshelf.data.remote.response.BookSearchDto
import com.example.bookshelf.data.remote.response.SearchResultDto
import com.example.bookshelf.domain.model.Book
import com.example.bookshelf.domain.model.BookPreview

fun SearchResultDto.toBookPreviewList(): List<BookPreview> {
    return items.map { book->
        BookPreview(
            book.id,
            book.volumeInfo.title ?: "",
            book.volumeInfo.imageLinks?.smallThumbnail?.replace("http", "https")
        )
    }
}

fun BookSearchDto.toBook(): Book {
    return Book(
        id = id,
        title = volumeInfo.title,
        authors = volumeInfo.authors ?: emptyList(),
        publisher = volumeInfo.publisher ?: "",
        publishedDate = volumeInfo.publishedDate ?: "",
        description = volumeInfo.description ?: "",
        pageCount = volumeInfo.pageCount ?: 0,
        categories = volumeInfo.categories ?: emptyList(),
        thumbnail = volumeInfo.imageLinks?.smallThumbnail?.replace("http", "https") ?: ""
    )
}