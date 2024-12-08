package com.inner.second.feature.contract

import android.annotation.SuppressLint
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.inner.second.core.designsystem.R
import com.inner.second.core.designsystem.SecondButton
import com.inner.second.core.designsystem.SecondToolbar
import com.inner.second.core.designsystem.theme.Background
import com.inner.second.core.designsystem.theme.Green
import com.inner.second.core.designsystem.theme.Purple
import com.inner.second.core.model.ContractType
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SecondContractMainRoute(
    contractViewModel: NewContractViewModel = hiltViewModel(),
    navigateToGetInfo: (ContractType) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) {
        SecondContractMainScreen(
            onButtonClick = { contractType ->
                contractViewModel.onContractTypeChange(contractType)
                navigateToGetInfo(contractType)
            },
            showSnackBar = {
                scope.launch {
                    snackBarHostState.showSnackbar(it)
                }
            }
        )
    }
}

@Composable
fun SecondContractMainScreen(
    onButtonClick: (ContractType) -> Unit,
    showSnackBar: (String) -> Unit,
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
            onButtonClick = onButtonClick,
            showSnackBar = showSnackBar,
        )
    }
}

@Composable
private fun SecondContractMainContent(
    onButtonClick: (ContractType) -> Unit,
    showSnackBar: (String) -> Unit
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
            text = "기존 계약서 스캔하기",
            onClick = { showSnackBar("슬픈 사연으로 인하여 구현하지 못하게 되었습니다...") }
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
            onClick = { showSnackBar("추후 오픈 예정입니다.") }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SecondContractScreenPreview() {
    SecondContractMainScreen(
        onButtonClick = {},
        showSnackBar = {},
    )
}