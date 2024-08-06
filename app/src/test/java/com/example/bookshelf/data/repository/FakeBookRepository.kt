package com.example.bookshelf.data.repository

import com.example.bookshelf.data.remote.mappers.toBook
import com.example.bookshelf.data.remote.mappers.toBookPreviewList
import com.example.bookshelf.data.remote.response.BookSearchDto
import com.example.bookshelf.data.remote.response.BookVolumeInfoDto
import com.example.bookshelf.data.remote.response.SearchItemDto
import com.example.bookshelf.data.remote.response.SearchResultDto
import com.example.bookshelf.domain.model.Book
import com.example.bookshelf.domain.model.BookPreview
import com.example.bookshelf.domain.repository.BookRepository
import com.example.bookshelf.domain.util.Resource

class FakeBookRepository: BookRepository {
    private val books = mutableListOf<SearchItemDto>()
    private val bookItem = BookSearchDto(
        id = "aaa",
        selfLink = "",
        volumeInfo = BookVolumeInfoDto(
            "Three As", null, null, null, null, null, null, null
        )
    )
    private var shouldReturnNetworkError = false
    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }
    override suspend fun getBooks(query: String): Resource<List<BookPreview>> {
        return if(shouldReturnNetworkError) {
            Resource.Error("Error")
        } else {
            Resource.Success(
                data = SearchResultDto(books.filter { book->
                    book.volumeInfo.title?.contains(query) ?: false
                }).toBookPreviewList()
            )
        }
    }

    override suspend fun getBookById(id: String): Resource<Book> {
        return if(shouldReturnNetworkError) {
            Resource.Error("Error")
        } else {
            Resource.Success(
                data = bookItem.toBook()
            )
        }
    }
}