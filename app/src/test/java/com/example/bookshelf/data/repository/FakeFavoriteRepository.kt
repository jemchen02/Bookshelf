package com.example.bookshelf.data.repository

import com.example.bookshelf.data.local.favorite.Favorite
import com.example.bookshelf.data.local.mappers.toBookPreview
import com.example.bookshelf.data.local.mappers.toFavorite
import com.example.bookshelf.data.source.FakeFavoriteData
import com.example.bookshelf.domain.model.Book
import com.example.bookshelf.domain.model.BookPreview
import com.example.bookshelf.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFavoriteRepository: FavoriteRepository {
    private val favorites = FakeFavoriteData.favoriteList.toMutableList()
    override fun getAllFavoritesStream(): Flow<List<BookPreview>> {
        return flow {
            emit(
                favorites.map {
                    it.toBookPreview()
                }
            )
        }
    }

    override fun isFavoriteStream(id: String): Flow<Boolean> {
        return flow {emit(favorites.any { it.id == id })}
    }

    override suspend fun addFavorite(book: Book) {
        favorites.add(book.toFavorite())
    }

    override suspend fun deleteFavorite(id: String) {
        favorites.removeAll { it.id == id }
    }
}