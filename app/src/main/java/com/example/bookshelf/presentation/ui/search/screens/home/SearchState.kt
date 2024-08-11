package com.example.bookshelf.presentation.ui.search.screens.home

import com.example.bookshelf.data.local.favorite.Favorite
import com.example.bookshelf.domain.model.BookPreview
import kotlinx.coroutines.flow.Flow

data class SearchState (
    val searchText: String = "",
    val searchResults: List<BookPreview>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)