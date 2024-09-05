package com.example.bookshelf.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookshelf.data.local.favorite.Favorite
import com.example.bookshelf.data.local.favorite.FavoriteDao

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class BookDatabase: RoomDatabase(){
    abstract fun favoriteDao(): FavoriteDao
}