package com.example.bookshelf.presentation.ui.search.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookshelf.presentation.ui.components.grid.BookGrid
import com.example.bookshelf.presentation.ui.search.SearchViewModel
import com.example.bookshelf.presentation.ui.search.screens.home.components.SearchBar

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onSelectBook: (String) -> Unit
) {
    val searchViewModel = hiltViewModel<SearchViewModel>()
    val searchState = searchViewModel.searchState.collectAsState().value
    val focusManager = LocalFocusManager.current
    Column (
        modifier = modifier.padding(contentPadding)
    ){
        SearchBar(
            searchState = searchState,
            onSearchChange = { searchViewModel.changeSearchText(it) },
            onSearchEnter = {
                searchViewModel.getBooks()
                focusManager.clearFocus()
            }
        )
        if(searchState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        searchState.searchResults?.let {books->
            BookGrid(
                books = books,
                onSelectBook = onSelectBook
            )
        }
    }
}