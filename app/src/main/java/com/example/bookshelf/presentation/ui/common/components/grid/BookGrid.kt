package com.example.bookshelf.presentation.ui.common.components.grid

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bookshelf.domain.model.BookPreview

@Composable
fun BookGrid(
    books: List<BookPreview>,
    modifier: Modifier = Modifier,
    onSelectBook: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp),
        modifier = modifier.padding(horizontal = 4.dp),
    ) {
        items(items = books) {
            book -> BookCard(
                book,
                modifier = Modifier.clickable { onSelectBook(book.id) }
            )
        }
    }
}