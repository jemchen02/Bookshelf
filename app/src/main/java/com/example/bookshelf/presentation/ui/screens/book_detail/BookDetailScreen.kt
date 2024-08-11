package com.example.bookshelf.presentation.ui.screens.book_detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.bookshelf.presentation.ui.BookshelfContentType
import com.example.bookshelf.presentation.ui.screens.book_detail.components.BookDetailDoubleColumn
import com.example.bookshelf.presentation.ui.screens.book_detail.components.BookDetailSingleColumn

@Composable
fun BookDetailScreen(
    bookDetailViewModel: BookDetailViewModel,
    contentPadding: PaddingValues,
    contentType: BookshelfContentType,
    modifier: Modifier = Modifier
) {
    val bookDetailState = bookDetailViewModel.bookState.collectAsState().value

    if(bookDetailState.isLoading) {
        CircularProgressIndicator()
    } else {
        if(contentType == BookshelfContentType.SingleColumn) {
            BookDetailSingleColumn(modifier, contentPadding, bookDetailState)
        } else {
            BookDetailDoubleColumn(modifier, contentPadding, bookDetailState)
        }
    }
}




