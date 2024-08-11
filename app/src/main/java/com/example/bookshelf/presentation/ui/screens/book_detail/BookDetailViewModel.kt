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
import com.example.bookshelf.domain.repository.FavoriteRepository
import com.example.bookshelf.domain.util.Resource
import com.example.bookshelf.BookshelfApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val favoriteRepository: FavoriteRepository
): ViewModel() {
    private val _bookState = MutableStateFlow(BookDetailState())
    val bookState = _bookState.asStateFlow()
    fun getBook(id: String) {
        viewModelScope.launch {
            _bookState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }
            when(val result = bookRepository.getBookById(id)) {
                is Resource.Success -> {
                    val book = result.data
                    book?.let {
                        val isFavorite = favoriteRepository.isFavoriteStream(book.id).first()
                        _bookState.update {
                            it.copy(
                                book = result.data,
                                isFavorite = isFavorite,
                                isLoading = false,
                                error = null
                            )
                        }
                    } ?: _bookState.update {
                        it.copy(
                            book = null,
                            isFavorite = false,
                            isLoading = false,
                            error = "Book not found"
                        )
                    }
                }
                is Resource.Error -> {
                    _bookState.update {
                        it.copy(
                            book = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }
    fun toggleFavorite() {
        val currBook = _bookState.value.book
        currBook?.let { book->
            viewModelScope.launch {
                val isFavorite = favoriteRepository.isFavoriteStream(book.id).first()
                if(isFavorite) {
                    favoriteRepository.deleteFavorite(book.id)
                } else {
                    favoriteRepository.addFavorite(book)
                }
                _bookState.update {
                    it.copy(
                        isFavorite = !isFavorite
                    )
                }
            }
        }
    }
}