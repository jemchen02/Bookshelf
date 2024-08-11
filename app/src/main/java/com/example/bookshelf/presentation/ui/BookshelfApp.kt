package com.example.bookshelf.presentation.ui

import androidx.compose.animation.Crossfade
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bookshelf.R
import com.example.bookshelf.presentation.ui.components.scaffold.BookshelfTopAppBar
import com.example.bookshelf.presentation.ui.components.scaffold.NavigationBottomBar
import com.example.bookshelf.presentation.ui.favorite.FavoritesTab
import com.example.bookshelf.presentation.ui.screens.book_detail.components.BookDetailActions
import com.example.bookshelf.presentation.ui.screens.book_detail.BookDetailScreen
import com.example.bookshelf.presentation.ui.screens.book_detail.BookDetailViewModel
import com.example.bookshelf.presentation.ui.search.SearchTab
import com.example.bookshelf.presentation.ui.search.screens.home.SearchScreen
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
