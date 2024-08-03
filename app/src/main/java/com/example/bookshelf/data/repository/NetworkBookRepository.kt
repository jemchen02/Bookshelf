package com.example.bookshelf.data.repository

import android.util.Log
import com.example.bookshelf.data.mappers.toBook
import com.example.bookshelf.data.mappers.toBookPreviewList
import com.example.bookshelf.data.remote.BookApiService
import com.example.bookshelf.domain.model.Book
import com.example.bookshelf.domain.model.BookPreview
import com.example.bookshelf.domain.repository.BookRepository
import com.example.bookshelf.domain.util.Resource

class NetworkBookRepository(
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