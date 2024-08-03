package com.example.bookshelf.presentation.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.domain.model.Book
import com.example.bookshelf.domain.model.BookPreview

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onSelectBook: (String) -> Unit
) {
    val searchViewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory)
    val searchState = searchViewModel.searchState

    Box(modifier = modifier.padding(contentPadding)) {
        BookshelfResultScreen(
            searchState = searchState,
            onSearchChange = { searchViewModel.changeSearchText(it) },
            onSearchEnter = {searchViewModel.getBooks()},
            onSelectBook = onSelectBook
        )
    }
}
@Composable
fun BookshelfResultScreen(
    searchState: SearchState,
    onSearchChange: (String) -> Unit,
    onSearchEnter: () -> Unit,
    onSelectBook: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        SearchBar(searchState = searchState, onSearchChange = onSearchChange, onSearchEnter = onSearchEnter)
        searchState.searchResults?.let {books->
            LazyVerticalGrid(
                columns = GridCells.Adaptive(200.dp),
                modifier = modifier.padding(horizontal = 4.dp),
            ) {
                items(items = books) {
                    book -> BookCard(
                        book,
                        modifier = Modifier.clickable { onSelectBook(book.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchState: SearchState,
    onSearchEnter: () -> Unit,
    onSearchChange: (String) -> Unit
) {
    TextField(
        value = searchState.searchText,
        onValueChange = onSearchChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        label = { Text(text = stringResource(R.string.search))},
        placeholder = {Text(text = stringResource(R.string.search_placeholder))},
        trailingIcon = {
            IconButton(
                onClick = onSearchEnter
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon)
                )
            }
        }
    )
}
@Composable
fun BookCard(book: BookPreview, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        if(book.thumbnail != null) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.thumbnail)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = book.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.7f)
                    .padding(4.dp)
            )
        }
        else {
            Text(text = book.title)
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Text("Loading")
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Text("Error")
}