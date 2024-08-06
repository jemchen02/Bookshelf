package com.example.bookshelf.data.remote

import com.example.bookshelf.data.remote.mappers.toBook
import com.example.bookshelf.data.remote.mappers.toBookPreviewList
import com.example.bookshelf.domain.model.Book
import com.example.bookshelf.domain.model.BookPreview
import com.example.bookshelf.domain.repository.BookRepository
import com.example.bookshelf.domain.util.Resource
import javax.inject.Inject

class NetworkBookRepository @Inject constructor(
    private val bookApiService: BookApiService
): BookRepository {
    override suspend fun getBooks(query: String): Resource<List<BookPreview>> {
        return try {
            Resource.Success(
                data = bookApiService.getBooks(query).toBookPreviewList()
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown exception occurred")
        }
    }
    override suspend fun getBookById(id: String): Resource<Book> {
        return try {
            Resource.Success(
                data = bookApiService.getBookById(id).toBook()
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown exception occurred")
        }
    }
}