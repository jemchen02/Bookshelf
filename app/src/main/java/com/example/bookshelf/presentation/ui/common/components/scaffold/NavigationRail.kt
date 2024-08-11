package com.example.bookshelf.presentation.ui.common.components.scaffold

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookshelf.presentation.ui.util.TabType
import com.example.bookshelf.presentation.ui.util.navigationItemList

@Composable
fun BookshelfNavigationRail(
    onTabPress: (TabType) -> Unit,
    currentTab: TabType
) {
    NavigationRail (
        containerColor = Color(0XFFFAF0E6)
    ){
        navigationItemList.map {
            NavigationRailItem(
                selected = currentTab == it.tabType,
                onClick = { onTabPress(it.tabType) },
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.tabType.name,
                        modifier = Modifier.size(40.dp)

                    )
                },
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}
@Composable
@Preview
fun NavigationRailPreview() {
    BookshelfNavigationRail(
        onTabPress = {},
        currentTab = TabType.Search
    )
}