package com.example.bookshelf.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bookshelf.data.local.favorite.Favorite
import com.example.bookshelf.data.local.favorite.FavoriteDao

@Database(entities = arrayOf(Favorite::class), version = 1, exportSchema = false)
abstract class BookDatabase: RoomDatabase(){
    abstract fun favoriteDao(): FavoriteDao
    companion object {
        @Volatile
        private var INSTANCE: BookDatabase? = null
        fun getDatabase(context: Context): BookDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    BookDatabase::class.java,
                    "book_database"
                )
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }

}