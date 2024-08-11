package com.example.bookshelf.presentation.ui.util

import com.example.bookshelf.domain.model.Book

data class BookshelfUiState(
    val currentTab: TabType = enumValues<TabType>().first()
)