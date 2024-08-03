package com.example.bookshelf.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bookshelf.R
import com.example.bookshelf.presentation.ui.screens.book_detail.BookDetailScreen
import com.example.bookshelf.presentation.ui.screens.home.HomeScreen

enum class BookshelfContentType {
    SingleColumn, DoubleColumn
}
enum class BookshelfAppScreen() {
    Start, Book
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfApp(windowSize: WindowWidthSizeClass) {
    val bookshelfViewModel : BookshelfViewModel = viewModel(factory = BookshelfViewModel.Factory)
    val uiState = bookshelfViewModel.bookshelfUiState
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = BookshelfAppScreen.valueOf(
        backStackEntry?.destination?.route ?: BookshelfAppScreen.Start.name
    )
    val currTitle = when(currentScreen) {
        BookshelfAppScreen.Start -> stringResource(R.string.app_name)
        BookshelfAppScreen.Book -> uiState.selectedBook?.title ?: stringResource(R.string.book)
    }
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
    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BookshelfTopAppBar(
                scrollBehavior = scrollBehavior,
                currTitle = currTitle,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ){innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BookshelfAppScreen.Start.name
        ) {
            composable(BookshelfAppScreen.Start.name) {
                HomeScreen(
                    bookshelfUiState = bookshelfViewModel.bookshelfUiState,
                    contentPadding = innerPadding,
                    onSearchChange = {bookshelfViewModel.changeSearchText(it)},
                    onSearchEnter = {bookshelfViewModel.getBooks()},
                    onSelectBook = {
                        bookshelfViewModel.selectBook(it)
                        navController.navigate(BookshelfAppScreen.Book.name)
                    }
                )
            }
            composable(BookshelfAppScreen.Book.name) {
                BookDetailScreen(
                    book = bookshelfViewModel.bookshelfUiState.selectedBook,
                    contentPadding = innerPadding,
                    contentType = contentType
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfTopAppBar(
    currTitle: String,
    scrollBehavior: TopAppBarScrollBehavior,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = currTitle,
                style = MaterialTheme.typography.headlineMedium,
            )
        },
        navigationIcon = {
            if(canNavigateBack){
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )

                }
            }
        },
        modifier = modifier
    )
}