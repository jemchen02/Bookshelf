package com.example.bookshelf.presentation.ui.screens.home

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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.domain.model.Book
import com.example.bookshelf.presentation.BookshelfUiState

@Composable
fun HomeScreen(
    bookshelfUiState: BookshelfUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onSearchChange: (String) -> Unit,
    onSearchEnter: () -> Unit,
    onSelectBook: (Book) -> Unit
) {
    Box(modifier = Modifier.padding(contentPadding)) {
        when (bookshelfUiState) {
            is BookshelfUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is BookshelfUiState.Success -> BookshelfResultScreen(
                books = bookshelfUiState.books,
                bookshelfUiState = bookshelfUiState,
                onSearchChange = onSearchChange,
                onSearchEnter = onSearchEnter,
                onSelectBook = onSelectBook
            )
            is BookshelfUiState.Idle -> BookshelfSearchScreen(
                bookshelfUiState = bookshelfUiState,
                onSearchChange = onSearchChange,
                onSearchEnter = onSearchEnter)
            is BookshelfUiState.Error -> ErrorScreen({ }, modifier = modifier.fillMaxSize())
        }
    }
}
@Composable
fun BookshelfResultScreen(
    books: List<Book>,
    bookshelfUiState: BookshelfUiState,
    onSearchChange: (String) -> Unit,
    onSearchEnter: () -> Unit,
    onSelectBook: (Book) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        SearchBar(bookshelfUiState = bookshelfUiState, onSearchChange = onSearchChange, onSearchEnter = onSearchEnter)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(200.dp),
            modifier = modifier.padding(horizontal = 4.dp),
        ) {
            items(items = books, key = { book -> book.id }) {
                book -> BookCard(
                    book,
                    modifier = Modifier.clickable { onSelectBook(book) }
                )
            }
        }
    }
}
@Composable
fun BookshelfSearchScreen(
    bookshelfUiState: BookshelfUiState,
    onSearchChange: (String) -> Unit,
    onSearchEnter: () -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(bookshelfUiState = bookshelfUiState, onSearchChange = onSearchChange, onSearchEnter = onSearchEnter)
}
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    bookshelfUiState: BookshelfUiState,
    onSearchEnter: () -> Unit,
    onSearchChange: (String) -> Unit
) {
    TextField(
        value = bookshelfUiState.searchText,
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
fun BookCard(book: Book, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
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
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Text("Loading")
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Text("Error")
}