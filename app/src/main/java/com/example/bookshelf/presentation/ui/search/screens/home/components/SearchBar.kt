package com.example.bookshelf.presentation.ui.search.screens.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bookshelf.R
import com.example.bookshelf.presentation.ui.search.screens.home.SearchState

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchState: SearchState,
    onSearchEnter: () -> Unit,
    onSearchChange: (String) -> Unit
) {
    TextField(
        value = searchState.searchText,
        onValueChange = onSearchChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(50)),
        label = { Text(text = stringResource(R.string.search)) },
        placeholder = { Text(text = stringResource(R.string.search_placeholder)) },
        trailingIcon = {
            IconButton(
                onClick = onSearchEnter
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon)
                )
            }
        }
    )
}