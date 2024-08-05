package com.example.bookshelf.presentation.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.data.local.favorite.Favorite
import com.example.bookshelf.domain.model.Book
import com.example.bookshelf.domain.repository.BookRepository
import com.example.bookshelf.domain.repository.FavoriteRepository
import com.example.bookshelf.domain.util.Resource
import com.example.bookshelf.presentation.BookshelfApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SearchViewModel (
    private val bookRepository: BookRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel(){
    var searchState by mutableStateOf(SearchState())
        private set

    fun getAllFavorites(): Flow<List<Favorite>> = favoriteRepository.getAllFavoritesStream()

    fun changeSearchText(query: String) {
        searchState = searchState.copy(
            searchText = query
        )
    }
    fun getBooks() {
        if(searchState.searchText.isEmpty()) {
            searchState = SearchState()
        } else {
            viewModelScope.launch {
                searchState = searchState.copy(
                    isLoading = true,
                    error = null
                )
                when(val result = bookRepository.getBooks(searchState.searchText)) {
                    is Resource.Success -> {
                        searchState = searchState.copy(
                            searchText = "",
                            searchResults = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        searchState = searchState.copy(
                            searchText = "",
                            searchResults = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                SearchViewModel(
                    bookRepository = application.container.bookRepository,
                    favoriteRepository = application.container.favoriteRepository
                )
            }
        }
    }
}