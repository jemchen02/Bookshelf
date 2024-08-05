package com.example.bookshelf.presentation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookshelf.R
import com.example.bookshelf.domain.model.Book
import com.example.bookshelf.presentation.ui.components.BookshelfTopAppBar
import com.example.bookshelf.presentation.ui.screens.book_detail.BookDetailActions
import com.example.bookshelf.presentation.ui.screens.book_detail.BookDetailScreen
import com.example.bookshelf.presentation.ui.screens.book_detail.BookDetailViewModel
import com.example.bookshelf.presentation.ui.screens.search.SearchViewModel
import com.example.bookshelf.presentation.ui.screens.search.SearchScreen

enum class BookshelfContentType {
    SingleColumn, DoubleColumn
}
enum class BookshelfAppScreen() {
    Start, Book
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfApp(windowSize: WindowWidthSizeClass) {
    val bookDetailViewModel: BookDetailViewModel = viewModel(factory = BookDetailViewModel.Factory)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val navController = rememberNavController()
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
    val currentScreen = BookshelfAppScreen.valueOf(
        backStackEntry?.destination?.route ?: BookshelfAppScreen.Start.name
    )
    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BookshelfTopAppBar(
                scrollBehavior = scrollBehavior,
                currTitle = currTitle,
                canNavigateBack = currentScreen != BookshelfAppScreen.Start,
                navigateUp = { navController.navigateUp() },
            ) {
                if(currentScreen == BookshelfAppScreen.Book) {
                    BookDetailActions (
                        isFavorite = bookDetailViewModel.getIsFavorite().collectAsState(initial = false).value,
                    ){
                        bookDetailViewModel.toggleFavorite()
                    }
                }
            }
        },
        containerColor = Color(0XFFFAF0E6)
    ){innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BookshelfAppScreen.Start.name
        ) {
            composable(BookshelfAppScreen.Start.name) {
                SearchScreen(
                    contentPadding = innerPadding,
                    onSelectBook = { bookId ->
                        bookDetailViewModel.getBook(bookId)
                        navController.navigate(BookshelfAppScreen.Book.name)
                    }
                )
            }
            composable(
                route = BookshelfAppScreen.Book.name,
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
