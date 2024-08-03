package com.example.bookshelf.domain.repository

import com.example.bookshelf.domain.model.Book
import com.example.bookshelf.domain.model.BookPreview
import com.example.bookshelf.domain.util.Resource

interface BookRepository {
    suspend fun getBooks(query: String): Resource<List<BookPreview>>
    suspend fun getBookById(id: String): Resource<Book>
}