package com.example.bookshelf.presentation.ui.search

import androidx.compose.foundation.layout.PaddingValues
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bookshelf.R
import com.example.bookshelf.presentation.ui.BookshelfContentType
import com.example.bookshelf.presentation.ui.BookshelfViewModel
import com.example.bookshelf.presentation.ui.components.scaffold.BookshelfTopAppBar
import com.example.bookshelf.presentation.ui.components.scaffold.NavigationBottomBar
import com.example.bookshelf.presentation.ui.screens.book_detail.BookDetailScreen
import com.example.bookshelf.presentation.ui.screens.book_detail.BookDetailViewModel
import com.example.bookshelf.presentation.ui.screens.book_detail.components.BookDetailActions
import com.example.bookshelf.presentation.ui.search.screens.home.SearchScreen
import com.example.bookshelf.presentation.ui.util.TabType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTab(
    windowSize: WindowWidthSizeClass,
    navController: NavHostController,
    onTabPress: (TabType) -> Unit
) {
    val bookDetailViewModel = hiltViewModel<BookDetailViewModel>()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val currTitle = stringResource(R.string.app_name)
    val contentType = when(windowSize) {
        WindowWidthSizeClass.Compact ->
            BookshelfContentType.SingleColumn
        WindowWidthSizeClass.Medium ->
            BookshelfContentType.DoubleColumn
        WindowWidthSizeClass.Expanded ->
            BookshelfContentType.DoubleColumn
        else ->
            BookshelfContentType.SingleColumn
    }
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = SearchScreens.valueOf(
        backStackEntry?.destination?.route ?: SearchScreens.Start.name
    )
    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BookshelfTopAppBar(
                scrollBehavior = scrollBehavior,
                currTitle = currTitle,
                canNavigateBack = currentScreen != SearchScreens.Start,
                navigateUp = { navController.navigateUp() },
            ) {
                if(currentScreen == SearchScreens.Book) {
                    BookDetailActions (
                        isFavorite = bookDetailViewModel.bookState.collectAsState().value.isFavorite,
                    ){
                        bookDetailViewModel.toggleFavorite()
                    }
                }
            }
        },
        bottomBar = {
            NavigationBottomBar(
                onTabPress = onTabPress,
                currentTab = TabType.Search
            )
        },
        containerColor = Color(0XFFFAF0E6)
    ){innerPadding ->
        NavHost(
            navController = navController,
            startDestination = SearchScreens.Start.name
        ) {
            composable(SearchScreens.Start.name) {
                SearchScreen(
                    contentPadding = innerPadding,
                    onSelectBook = { bookId ->
                        bookDetailViewModel.getBook(bookId)
                        navController.navigate(SearchScreens.Book.name)
                    }
                )
            }
            composable(
                route = SearchScreens.Book.name,
            ) {
                BookDetailScreen(
                    bookDetailViewModel = bookDetailViewModel,
                    contentPadding = innerPadding,
                    contentType = contentType
                )
            }
        }
    }
}