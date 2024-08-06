package com.example.bookshelf.data.local

import com.example.bookshelf.data.local.favorite.Favorite
import com.example.bookshelf.data.local.favorite.FavoriteDao
import com.example.bookshelf.data.local.mappers.toFavorite
import com.example.bookshelf.domain.model.Book
import com.example.bookshelf.domain.model.BookPreview
import com.example.bookshelf.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFavoriteRepository @Inject constructor(
    private val favoriteDao: FavoriteDao
): FavoriteRepository {
    override fun getAllFavoritesStream(): Flow<List<Favorite>> =
        favoriteDao.getAllFavorites()

    override fun isFavoriteStream(id: String): Flow<Boolean> =
        favoriteDao.isFavorite(id)

    override suspend fun addFavorite(book: Book) =
        favoriteDao.addFavorite(book.toFavorite())

    override suspend fun deleteFavorite(id: String) =
        favoriteDao.deleteFavorite(id)

}