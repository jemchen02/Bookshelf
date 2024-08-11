package com.example.bookshelf.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshelf.domain.repository.BookRepository
import com.example.bookshelf.domain.util.Resource
import com.example.bookshelf.presentation.ui.search.screens.home.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val bookRepository: BookRepository,
) : ViewModel(){
    private val _searchState = MutableStateFlow(SearchState())
    val searchState = _searchState.asStateFlow()

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