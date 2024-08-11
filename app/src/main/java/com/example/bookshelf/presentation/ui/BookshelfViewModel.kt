package com.example.bookshelf.presentation.ui

import androidx.lifecycle.ViewModel
import com.example.bookshelf.domain.model.Book
import com.example.bookshelf.presentation.ui.util.BookshelfUiState
import com.example.bookshelf.presentation.ui.util.TabType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookshelfViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(BookshelfUiState())
    val uiState = _uiState.asStateFlow()
    fun updateTab(tabType: TabType) {
        _uiState.update {
            it.copy(
                currentTab = tabType
            )
        }
    }
}