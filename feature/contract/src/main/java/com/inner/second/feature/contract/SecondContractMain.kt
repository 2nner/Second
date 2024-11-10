package com.inner.second.feature.contract

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inner.second.core.designsystem.R
import com.inner.second.core.designsystem.SecondButton
import com.inner.second.core.designsystem.SecondToolbar
import com.inner.second.core.designsystem.theme.Background
import com.inner.second.core.designsystem.theme.Green
import com.inner.second.core.designsystem.theme.Purple
import com.inner.second.core.model.ContractType

@Composable
fun SecondContractMainRoute(
    onButtonClick: (ContractType) -> Unit
) {
    SecondContractMainScreen(onButtonClick = onButtonClick)
}

@Composable
fun SecondContractMainScreen(
    onButtonClick: (ContractType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(Background)
    ) {
        SecondToolbar(
            title = "새 계약서 만들기",
            onBackButtonClick = { /* TODO : implement back button */ }
        )
        SecondContractMainContent(
            onButtonClick = onButtonClick
        )
    }
}

@Composable
private fun SecondContractMainContent(
    onButtonClick: (ContractType) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        SecondButton(
            titleIconRes = R.drawable.ic_edit_outlined,
            text = "직접 작성하기",
            onClick = { onButtonClick(ContractType.New) }
        )
        Spacer(modifier = Modifier.height(12.dp))
        SecondButton(
            titleIconRes = R.drawable.ic_document_outlined,
            text = "기존 계약서 스캔하기"
        )
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "세컨드에서 제공하는 양식",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        SecondButton(
            titleIconRes = R.drawable.ic_money_outlined,
            trailingIconRes = R.drawable.ic_chevron_right,
            text = "차용증",
            titleIconTintColor = Green,
            onClick = { onButtonClick(ContractType.IOU) }
        )
        Spacer(modifier = Modifier.height(12.dp))
        SecondButton(
            titleIconRes = R.drawable.ic_money_outlined,
            trailingIconRes = R.drawable.ic_chevron_right,
            text = "레슨 · 과외 표준계약서",
            titleIconTintColor = Purple,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SecondContractScreenPreview() {
    SecondContractMainScreen(
        onButtonClick = {}
    )
}