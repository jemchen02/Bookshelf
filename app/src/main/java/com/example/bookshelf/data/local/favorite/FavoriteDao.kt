package com.example.bookshelf.data.local.favorite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert
    suspend fun addFavorite(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE id = :id")
    suspend fun deleteFavorite(id: String)

    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Query("SELECT COUNT(*) > 0 FROM favorite WHERE id = :id")
    fun isFavorite(id: String): Flow<Boolean>
}