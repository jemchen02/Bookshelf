package com.example.bookshelf.data.repository

import android.util.Log
import com.example.bookshelf.domain.model.Book
import com.example.bookshelf.data.remote.SearchResultDto
import com.example.bookshelf.data.remote.BookApiService
import com.example.bookshelf.domain.repository.BookRepository

class NetworkBookRepository(
    private val bookApiService: BookApiService
): BookRepository {
    override suspend fun getBooks(query: String): List<Book> {
        Log.d("BookApiService", "Query: $query")
        val searchResult: SearchResultDto = bookApiService.getBooks(query)
        Log.d("BookApiService", "Books: $searchResult")
        val searchList = searchResult.items

        return searchList.map {
            val vInfo = it.volumeInfo
            Book(
                it.id,
                vInfo.title,
                vInfo.authors,
                vInfo.publishedDate,
                vInfo.description,
                vInfo.categories,
                vInfo.imageLinks.smallThumbnail.replace("http", "https")
            )
        }
    }
}