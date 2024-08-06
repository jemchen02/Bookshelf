package com.example.bookshelf.data.remote

import com.example.bookshelf.BuildConfig
import com.example.bookshelf.data.remote.response.BookSearchDto
import com.example.bookshelf.data.remote.response.SearchResultDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApiService {
    @GET("/books/v1/volumes")
    suspend fun getBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 20,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): SearchResultDto
    @GET("/books/v1/volumes/{id}")
    suspend fun getBookById(
        @Path("id") id: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): BookSearchDto
}