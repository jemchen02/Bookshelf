package com.example.bookshelf.presentation.ui.screens.search

import com.example.bookshelf.data.local.favorite.Favorite
import com.example.bookshelf.domain.model.BookPreview
import kotlinx.coroutines.flow.Flow

data class SearchState (
    val searchText: String = "",
    val searchResults: List<BookPreview>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)