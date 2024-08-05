package com.example.bookshelf.data.local.mappers

import com.example.bookshelf.data.local.favorite.Favorite
import com.example.bookshelf.domain.model.Book
import com.example.bookshelf.domain.model.BookPreview

fun Favorite.toBookPreview(): BookPreview {
    return BookPreview(
        id = id,
        title = title,
        thumbnail = thumbnail
    )
}
fun Book.toFavorite(): Favorite {
    return Favorite(
        id = id,
        title = title,
        thumbnail = thumbnail
    )
}