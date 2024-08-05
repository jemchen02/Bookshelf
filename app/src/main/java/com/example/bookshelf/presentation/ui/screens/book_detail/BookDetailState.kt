package com.example.bookshelf.presentation.ui.screens.book_detail

import com.example.bookshelf.domain.model.Book

data class BookDetailState(
    val book: Book? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
