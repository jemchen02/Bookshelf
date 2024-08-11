package com.example.bookshelf.data.repository

import com.example.bookshelf.data.remote.mappers.toBook
import com.example.bookshelf.data.remote.mappers.toBookPreviewList
import com.example.bookshelf.data.remote.response.BookSearchDto
import com.example.bookshelf.data.remote.response.BookVolumeInfoDto
import com.example.bookshelf.data.remote.response.SearchItemDto
import com.example.bookshelf.data.remote.response.SearchResultDto
import com.example.bookshelf.data.source.FakeBookData.bookItem
import com.example.bookshelf.data.source.FakeBookData.bookList
import com.example.bookshelf.domain.model.Book
import com.example.bookshelf.domain.model.BookPreview
import com.example.bookshelf.domain.repository.BookRepository
import com.example.bookshelf.domain.util.Resource

class FakeBookRepository: BookRepository {
    var shouldReturnNetworkError = false
    var cannotFindBook = false

    override suspend fun getBooks(query: String): Resource<List<BookPreview>> {
        return if(shouldReturnNetworkError) {
            Resource.Error("Network Error")
        } else {
            Resource.Success(
                data = SearchResultDto(bookList).toBookPreviewList()
            )
        }
    }

    override suspend fun getBookById(id: String): Resource<Book> {
        return if(shouldReturnNetworkError) {
            Resource.Error("Network Error")
        } else if(cannotFindBook) {
            Resource.Success(
                data = null
            )
        } else {
            Resource.Success(
                data = bookItem.toBook()
            )
        }
    }
}