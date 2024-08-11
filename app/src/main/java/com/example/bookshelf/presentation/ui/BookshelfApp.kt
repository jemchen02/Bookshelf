package com.example.bookshelf.presentation.ui

import androidx.compose.animation.Crossfade
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.bookshelf.presentation.ui.favorite.FavoritesTab
import com.example.bookshelf.presentation.ui.search.SearchTab
import com.example.bookshelf.presentation.ui.util.TabType

enum class BookshelfContentType {
    SingleColumn, DoubleColumn
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfApp(windowSize: WindowWidthSizeClass) {
    val bookshelfViewModel: BookshelfViewModel = viewModel()
    val currentTab = bookshelfViewModel.uiState.collectAsState().value.currentTab
    val searchNavController = rememberNavController()
    val favoritesNavController = rememberNavController()
    Crossfade(targetState = currentTab) { tab ->
        when (tab) {
            TabType.Search -> {
                SearchTab(
                    windowSize = windowSize,
                    navController = searchNavController,
                    onTabPress = {
                        bookshelfViewModel.updateTab(it)
                        searchNavController.navigateUp()
                    }
                )
            }
            TabType.Favorites -> {
                FavoritesTab(
                    windowSize = windowSize,
                    navController = favoritesNavController,
                    onTabPress = {
                        bookshelfViewModel.updateTab(it)
                        favoritesNavController.navigateUp()
                    }
                )
            }
        }
    }
}
