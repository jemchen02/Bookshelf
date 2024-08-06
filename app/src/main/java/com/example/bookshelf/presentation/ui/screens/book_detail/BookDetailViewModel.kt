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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val favoriteRepository: FavoriteRepository
): ViewModel() {
    var bookState by mutableStateOf(BookDetailState())
        private set
    fun getIsFavorite(): Flow<Boolean> {
        return bookState.book?.let{book->
            favoriteRepository.isFavoriteStream(book.id)
        } ?: flow{
            emit(false)
        }
    }
    fun toggleFavorite() {
        bookState.book?.let { book->
            viewModelScope.launch {
                val isFavorite = favoriteRepository.isFavoriteStream(book.id).first()
                if(isFavorite) {
                    favoriteRepository.deleteFavorite(book.id)
                } else {
                    favoriteRepository.addFavorite(book)
                }
            }
        }

    }
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
}