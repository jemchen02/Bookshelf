package com.example.bookshelf.presentation.ui.screens.book_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.presentation.ui.screens.book_detail.BookDetailState

@Composable
fun BookDetailSingleColumn(
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
                book.title,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                "Author(s): ${book.authors.joinToString(", ")}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                "Published ${book.publishedDate}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                "${book.pageCount} pages",
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