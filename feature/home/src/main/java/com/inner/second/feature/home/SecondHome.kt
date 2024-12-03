package com.inner.second.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.inner.second.core.designsystem.SecondBottomNavigationBar
import com.inner.second.core.designsystem.SecondToolbar
import com.inner.second.core.designsystem.theme.Background

@Composable
fun SecondHomeRoute(
    navigateToContractMain: () -> Unit,
) {
    SecondHomeScreen(
        navigateToContractMain = navigateToContractMain
    )
}

@Composable
fun SecondHomeScreen(
    navigateToContractMain: () -> Unit,
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val toolbarTitle by remember(selectedTab) {
        when (selectedTab) {
            0 -> mutableStateOf("내 계약서")
            else -> mutableStateOf("내 프로필")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(color = Background),
    ) {
        SecondToolbar(
            title = toolbarTitle,
            enableLtr = true
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            when (selectedTab) {
                0 -> SecondContractList()
                else -> SecondProfile()
            }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            SecondBottomNavigationBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                selectedTabIndex = selectedTab,
                onAddIconButtonClick = navigateToContractMain,
                onTabClick = { selectedTab = it }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondHomePreview() {
    SecondHomeRoute(
        navigateToContractMain = {}
    )
}