package com.example.bookshelf.presentation.ui.components.scaffold

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookshelf.presentation.ui.util.TabType
import com.example.bookshelf.presentation.ui.util.navigationItemList

@Composable
fun NavigationBottomBar(
    onTabPress: (TabType) -> Unit,
    currentTab: TabType
) {
    NavigationBar() {
        navigationItemList.map {
            NavigationBarItem(
                selected = currentTab == it.tabType,
                onClick = { onTabPress(it.tabType) },
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.tabType.name,
                        modifier = Modifier.size(32.dp)
                    )
                }
            )
        }
    }
}

@Composable
@Preview
fun NavigationBottomBarPreview() {
    NavigationBottomBar(onTabPress = {}, currentTab = TabType.Search)
}