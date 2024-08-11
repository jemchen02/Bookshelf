package com.example.bookshelf.data.source

import com.example.bookshelf.data.local.favorite.Favorite

object FakeFavoriteData {
    val favoriteList = listOf(
        Favorite(
            "111",
            "Triple ones",
            null
        ),
        Favorite(
            "222",
            "Triple twos",
            null
        )
    )
}