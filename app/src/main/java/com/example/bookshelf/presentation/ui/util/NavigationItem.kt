package com.example.bookshelf.presentation.ui.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val icon: ImageVector,
    val tabType: TabType
)
val navigationItemList = listOf(
    NavigationItem(
        icon = Icons.Default.Star,
        tabType = TabType.Favorites
    ),
    NavigationItem(
        icon = Icons.Default.Search,
        tabType = TabType.Search
    )
)