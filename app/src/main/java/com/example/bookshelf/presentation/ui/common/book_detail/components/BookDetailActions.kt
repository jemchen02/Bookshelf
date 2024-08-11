package com.example.bookshelf.presentation.ui.common.book_detail.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BookDetailActions(
    isFavorite: Boolean,
    onAddBook: () -> Unit
) {
    IconButton(onClick = onAddBook) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Save book",
            tint = if(isFavorite) Color.Yellow else Color.Black
        )
    }
}