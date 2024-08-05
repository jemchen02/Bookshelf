package com.example.bookshelf.data.local.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey
    val id: String,
    val title: String,
    val thumbnail: String?
)
