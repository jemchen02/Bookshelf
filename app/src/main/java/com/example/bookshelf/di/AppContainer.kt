package com.example.bookshelf.di

import android.content.Context
import com.example.bookshelf.data.local.BookDatabase
import com.example.bookshelf.data.local.OfflineFavoriteRepository
import com.example.bookshelf.data.remote.NetworkBookRepository
import com.example.bookshelf.data.remote.BookApiService
import com.example.bookshelf.domain.repository.BookRepository
import com.example.bookshelf.domain.repository.FavoriteRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {
    val bookRepository: BookRepository
    val favoriteRepository: FavoriteRepository
}
class DefaultAppContainer(context: Context): AppContainer {
    private val baseUrl = "https://www.googleapis.com/books/v1/"
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(baseUrl)
        .build()
    private val retrofitService: BookApiService by lazy {
        retrofit.create(BookApiService::class.java)
    }
    override val bookRepository: BookRepository by lazy {
        NetworkBookRepository(retrofitService)
    }
    override val favoriteRepository: FavoriteRepository by lazy {
        val bookDatabase = BookDatabase.getDatabase(context)
        OfflineFavoriteRepository(bookDatabase.favoriteDao())
    }
}