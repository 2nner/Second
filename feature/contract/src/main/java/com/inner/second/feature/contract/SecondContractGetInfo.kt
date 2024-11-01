package com.inner.second.feature.contract

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.inner.second.core.designsystem.SecondToolbar
import com.inner.second.core.designsystem.theme.Background

@Composable
fun SecondContractGetInfo(
    newContractViewModel: NewContractViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(Background)
    ) {
        SecondToolbar(
            title = "${newContractViewModel.contractType.getContractName()} 만들기",
            onBackButtonClick = { onBackButtonClick() }
        )
        SecondContractGetInfoContent()
    }
}

@Composable
fun SecondContractGetInfoContent() {

}

@Preview
@Composable
fun SecondContractGetInfoPreview() {
    SecondContractGetInfo(
        onBackButtonClick = {}
    )
}