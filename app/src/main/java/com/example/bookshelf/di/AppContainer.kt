package com.example.bookshelf.di

import com.example.bookshelf.data.repository.NetworkBookRepository
import com.example.bookshelf.data.remote.BookApiService
import com.example.bookshelf.domain.repository.BookRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val bookRepository: BookRepository
}
class DefaultAppContainer: AppContainer {
    private val baseUrl = "https://www.googleapis.com/books/v1/"
    private val json = Json {
        ignoreUnknownKeys = true
    }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()
    private val retrofitService: BookApiService by lazy {
        retrofit.create(BookApiService::class.java)
    }
    override val bookRepository: BookRepository by lazy {
        NetworkBookRepository(retrofitService)
    }
}