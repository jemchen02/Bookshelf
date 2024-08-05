package com.example.bookshelf.presentation.ui.screens.book_detail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.bookshelf.domain.model.Book
import com.example.bookshelf.domain.model.BookPreview

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