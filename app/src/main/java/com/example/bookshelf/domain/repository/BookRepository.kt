package com.example.bookshelf.domain.repository

import com.example.bookshelf.domain.model.Book

interface BookRepository {
    suspend fun getBooks(query: String): List<Book>
}