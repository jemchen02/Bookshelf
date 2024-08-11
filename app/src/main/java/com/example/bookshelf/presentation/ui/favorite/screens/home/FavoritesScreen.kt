package com.example.bookshelf.presentation.ui.favorite.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookshelf.presentation.ui.components.grid.BookGrid
import com.example.bookshelf.presentation.ui.favorite.FavoritesViewModel

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onSelectBook: (String) -> Unit
) {
    val favoritesViewModel = hiltViewModel<FavoritesViewModel>()
    val favorites = favoritesViewModel.getAllFavorites().collectAsState(emptyList()).value
    Column (
        modifier = modifier.padding(contentPadding)
    ){
        Column {
            Text(
                text = "Favorites",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(start = 16.dp)
            )
            BookGrid(
                books = favorites,
                onSelectBook = onSelectBook
            )
        }
    }
}