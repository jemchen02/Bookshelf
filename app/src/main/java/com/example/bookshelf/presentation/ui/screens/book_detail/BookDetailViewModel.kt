package com.example.bookshelf.presentation.ui.screens.book_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.domain.repository.BookRepository
import com.example.bookshelf.domain.util.Resource
import com.example.bookshelf.presentation.BookshelfApplication
import kotlinx.coroutines.launch

class BookDetailViewModel(private val bookRepository: BookRepository): ViewModel() {
    var bookState by mutableStateOf(BookDetailState())
        private set

    fun getBook(id: String) {
        viewModelScope.launch {
            bookState = bookState.copy(
                isLoading = true,
                error = null
            )
            when(val result = bookRepository.getBookById(id)) {
                is Resource.Success -> {
                    bookState = bookState.copy(
                        book = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    bookState = bookState.copy(
                        book = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val bookRepository = application.container.bookRepository
                BookDetailViewModel(bookRepository = bookRepository)
            }
        }
    }
}