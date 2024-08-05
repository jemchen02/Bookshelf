package com.example.bookshelf.domain.repository

import com.example.bookshelf.data.local.favorite.Favorite
import com.example.bookshelf.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository{
    fun getAllFavoritesStream(): Flow<List<Favorite>>
    fun isFavoriteStream(id: String): Flow<Boolean>
    suspend fun addFavorite(book: Book)
    suspend fun deleteFavorite(id: String)
}