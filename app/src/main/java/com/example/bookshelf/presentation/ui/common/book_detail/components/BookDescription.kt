package com.example.bookshelf.presentation.ui.common.book_detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.example.bookshelf.domain.model.Book
import com.google.android.material.textview.MaterialTextView

@Composable
fun BookDescription(book: Book, modifier: Modifier = Modifier) {
    val spannedText = HtmlCompat.fromHtml(book.description, 0)
    Box(modifier = modifier) {
        AndroidView(
            factory = { MaterialTextView(it) },
            update = {
                it.text = spannedText
                it.textSize = 20f
            }
        )
    }
}