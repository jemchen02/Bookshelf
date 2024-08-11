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
import com.example.bookshelf.domain.repository.BookRepository
import com.example.bookshelf.domain.repository.FavoriteRepository
import com.example.bookshelf.domain.util.Resource
import com.example.bookshelf.BookshelfApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel(){
    private val _searchState = MutableStateFlow(SearchState())
    val searchState = _searchState.asStateFlow()

    fun getAllFavorites(): Flow<List<Favorite>> = favoriteRepository.getAllFavoritesStream()

    fun changeSearchText(query: String) {
        _searchState.update {
            it.copy(searchText = query)
        }
    }
    fun getBooks() {
        if(_searchState.value.searchText.isEmpty()) {
            _searchState.value = SearchState()
        } else {
            viewModelScope.launch {
                _searchState.update {
                    it.copy(
                        isLoading = true,
                        error = null
                    )
                }
                when(val result = bookRepository.getBooks(_searchState.value.searchText)) {
                    is Resource.Success -> {
                        _searchState.update{
                            it.copy(
                                searchText = "",
                                searchResults = result.data,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _searchState.update {
                            it.copy(
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
    }
}