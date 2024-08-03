package com.example.bookshelf.presentation.ui.screens.book_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.presentation.BookshelfContentType
import com.example.bookshelf.R
import com.example.bookshelf.domain.model.Book

@Composable
fun BookDetailScreen(
    contentPadding: PaddingValues,
    contentType: BookshelfContentType,
    modifier: Modifier = Modifier
) {
    val bookDetailViewModel: BookDetailViewModel = viewModel(factory = BookDetailViewModel.Factory)
    val bookDetailState = bookDetailViewModel.bookState
    if(contentType == BookshelfContentType.SingleColumn) {
        BookDetailSingleColumn(modifier, contentPadding, bookDetailState)
    } else {
        BookDetailDoubleColumn(modifier, contentPadding, bookDetailState)
    }

}

@Composable
private fun BookDetailSingleColumn(
    modifier: Modifier,
    contentPadding: PaddingValues,
    bookDetailState: BookDetailState
) {
    bookDetailState.book?.let {book ->
        Column(
            modifier = modifier
                .padding(contentPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Author(s): ${book.authors.joinToString(", ")}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                "Published ${book.publishedDate}",
                style = MaterialTheme.typography.titleMedium
            )
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.book_image),
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 16.dp)
                    .height(300.dp)
            )
            BookDescription(book)
        }
    }
}
@Composable
private fun BookDetailDoubleColumn(
    modifier: Modifier,
    contentPadding: PaddingValues,
    bookDetailState: BookDetailState
) {
    bookDetailState.book?.let { book->
        Row (modifier = modifier.padding(contentPadding)){
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(book.thumbnail)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.book_image),
                    modifier = Modifier
                        .padding(horizontal = 32.dp, vertical = 16.dp)
                        .sizeIn(
                            maxWidth = 400.dp,  // Maximum width constraint
                            maxHeight = 400.dp  // Maximum height constraint
                        )
                        .fillMaxSize()
                )
                Text(
                    "Author(s): ${book.authors.joinToString(", ")}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "Published ${book.publishedDate}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            BookDescription(
                book,
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun BookDescription(book: Book, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            book.description,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}