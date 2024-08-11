package com.example.bookshelf.presentation.ui.screens.search

import android.graphics.Color
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.data.local.favorite.Favorite
import com.example.bookshelf.data.local.mappers.toBookPreview
import com.example.bookshelf.domain.model.Book
import com.example.bookshelf.domain.model.BookPreview
import com.example.bookshelf.presentation.ui.screens.book_detail.BookDetailViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onSelectBook: (String) -> Unit
) {
    val searchViewModel = hiltViewModel<SearchViewModel>()
    val searchState = searchViewModel.searchState.collectAsState().value
    val favorites = searchViewModel.getAllFavorites().collectAsState(emptyList()).value
    val favoriteBooks = favorites.map {
        it.toBookPreview()
    }
    Box(modifier = modifier.padding(contentPadding)) {
        BookshelfResultScreen(
            searchState = searchState,
            favorites = favoriteBooks,
            onSearchChange = { searchViewModel.changeSearchText(it) },
            onSearchEnter = {searchViewModel.getBooks()},
            onSelectBook = onSelectBook
        )
    }

}
@Composable
fun BookshelfResultScreen(
    searchState: SearchState,
    favorites: List<BookPreview>,
    onSearchChange: (String) -> Unit,
    onSearchEnter: () -> Unit,
    onSelectBook: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        SearchBar(searchState = searchState, onSearchChange = onSearchChange, onSearchEnter = onSearchEnter)
        searchState.searchResults?.let {books->
            BookGrid(
                books = books,
                onSelectBook = onSelectBook
            )
        } ?: Column {
            Text(
                text = "Favorites",
                style = MaterialTheme.typography.headlineSmall
            )
            BookGrid(
                books = favorites,
                onSelectBook = onSelectBook
            )
        }
        if(searchState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
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
            .padding(16.dp)
            .clip(RoundedCornerShape(50)),
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
@Preview
fun ImageExample() {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data("http://books.google.com/books/content?id=C1MI_4nZyD4C&printsec=frontcover&img=1&zoom=2&edge=curl&imgtk=AFLRE711lo_9uRUxTAjsPI5hHZDHTPT5xtQtCnV-gmg8-JK0aV-elupioXwbvHKmOpjFPMYCGpRxtkZnuV6zJrdXJH9m55CuxfgPiOU7vU69n4VDwdKRunf0p3QFwkuvCHUn0LPwsXXc&source=gbs_api")
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.7f)
            .padding(4.dp)
    )
}