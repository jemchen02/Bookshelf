package com.example.bookshelf.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.domain.model.Book
import com.example.bookshelf.domain.repository.BookRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BookshelfUiState {
    val searchText: String
    val selectedBook: Book?
    data class Success(
        val books: List<Book>,
        override val searchText: String,
        override val selectedBook: Book?
    ) : BookshelfUiState
    data class Idle(
        override val searchText: String,
        override val selectedBook: Book?
    ): BookshelfUiState
    object Error: BookshelfUiState {
        override val searchText: String = ""
        override val selectedBook: Book? = null
    }
    object Loading: BookshelfUiState {
        override val searchText: String = ""
        override val selectedBook: Book? = null
    }
}
class BookshelfViewModel (private val bookRepository: BookRepository) : ViewModel(){
    var bookshelfUiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Idle("", null))
        private set
    fun changeSearchText(query: String) {
        when(val currState = bookshelfUiState) {
            is BookshelfUiState.Success -> {
                bookshelfUiState = currState.copy(searchText = query)
            }
            is BookshelfUiState.Idle -> {
                bookshelfUiState = currState.copy(searchText = query)
            }
            else -> {}
        }
    }
    fun selectBook(book: Book) {
        val currState = bookshelfUiState
        if(currState is BookshelfUiState.Success) {
            bookshelfUiState = currState.copy(selectedBook = book)
        }
    }
    fun getBooks() {
        viewModelScope.launch {
            val query = bookshelfUiState.searchText
            bookshelfUiState = BookshelfUiState.Loading
            bookshelfUiState = try {
                BookshelfUiState.Success(bookRepository.getBooks(query), "", null)
            } catch(e: IOException) {
                BookshelfUiState.Error
            } catch(e: HttpException) {
                BookshelfUiState.Error
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val bookRepository = application.container.bookRepository
                BookshelfViewModel(bookRepository = bookRepository)
            }
        }
    }
}