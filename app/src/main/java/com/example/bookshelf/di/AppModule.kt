package com.example.bookshelf.di

import android.content.Context
import androidx.room.Room
import com.example.bookshelf.data.local.BookDatabase
import com.example.bookshelf.data.local.OfflineFavoriteRepository
import com.example.bookshelf.data.local.favorite.FavoriteDao
import com.example.bookshelf.data.remote.BookApiService
import com.example.bookshelf.data.remote.NetworkBookRepository
import com.example.bookshelf.domain.repository.BookRepository
import com.example.bookshelf.domain.repository.FavoriteRepository
import com.example.bookshelf.other.Consts.BASE_URL
import com.example.bookshelf.other.Consts.DATABASE_NAME
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideBookDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, BookDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideFavoriteDao(
        database: BookDatabase
    ) = database.favoriteDao()

    @Singleton
    @Provides
    fun provideBookApi(): BookApiService {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
            .create(BookApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideBookRepository(bookApi: BookApiService): BookRepository {
        return NetworkBookRepository(bookApi)
    }

    @Singleton
    @Provides
    fun provideFavoriteRepository(favoriteDao: FavoriteDao): FavoriteRepository{
        return OfflineFavoriteRepository(favoriteDao)
    }
}