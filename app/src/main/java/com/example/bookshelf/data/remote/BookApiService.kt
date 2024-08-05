package com.example.bookshelf.data.remote

import com.example.bookshelf.data.remote.dtos.BookSearchDto
import com.example.bookshelf.data.remote.dtos.SearchResultDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApiService {
    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 20
    ): SearchResultDto
    @GET("volumes/{id}")
    suspend fun getBookById(
        @Path("id") id: String
    ): BookSearchDto
}