package com.inner.second.feature.contract_receiver

import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.webkit.WebViewAssetLoader
import com.inner.second.core.designsystem.SecondActionButton
import com.inner.second.core.designsystem.SecondSignatureBottomSheet
import com.inner.second.core.designsystem.SecondTextBottomSheet
import com.inner.second.core.designsystem.SecondToolbar
import com.inner.second.core.designsystem.theme.Background
import com.inner.second.core.util.ContractUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SecondContractReceiverSignatureRoute(
    contractReceiverViewModel: ContractReceiverViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit,
    navigateToFinish: () -> Unit,
) {
    val confirmed by contractReceiverViewModel.confirmed.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    val textSheetState = rememberStandardBottomSheetState(
        skipHiddenState = false
    )
    val signatureSheetState = rememberStandardBottomSheetState(
        skipHiddenState = false
    )
    var showSignatureBottomSheet by remember { mutableStateOf(false) }
    var showConfirmBottomSheet by remember { mutableStateOf(false) }

    SecondContractReceiverSignatureScreen(
        onBackButtonClick = onBackButtonClick,
        onConfirmButtonClick = {
            showConfirmBottomSheet = true
        },
        onDrawSignatureButtonClick = {
            showSignatureBottomSheet = true
        },
        confirmed = confirmed
    )

    if (showConfirmBottomSheet) {
        SecondTextBottomSheet(
            text = "본인은 상기 계약서의 내용을 확인하였으며, 여기에 서명하는 것에 동의합니다.",
            onDismiss = { showConfirmBottomSheet = false },
            onConfirmButtonClick = {
                contractReceiverViewModel.updateConfirmedState(true)
            },
            sheetState = textSheetState
        )
    }

    if (showSignatureBottomSheet) {
        SecondSignatureBottomSheet(
            onDrawSignatureComplete = {
                coroutineScope.launch {
                    contractReceiverViewModel.updateContractStateConcluded()
                    delay(1000L)
                    navigateToFinish()
                }
            },
            onDismiss = { showSignatureBottomSheet = false },
            sheetState = signatureSheetState,
        )
    }
}

@Composable
fun SecondContractReceiverSignatureScreen(
    onBackButtonClick: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    onDrawSignatureButtonClick: () -> Unit,
    confirmed: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(Background)
    ) {
        SecondToolbar(
            title = "계약서 서명하기",
            onBackButtonClick = { onBackButtonClick() }
        )
        SecondContractWebView(
            modifier = Modifier.weight(1f),
            content = ContractUtil.contractDummy(),
        )
        SecondActionButton(
            text = "1단계 : 내용 확인하기",
            enabled = !confirmed,
            onButtonClick = {
                onConfirmButtonClick()
            },
        )
        SecondActionButton(
            text = "2단계 : 계약서 서명하기",
            enabled = confirmed,
            onButtonClick = {
                onDrawSignatureButtonClick()
            },
        )
    }
}

@Composable
fun SecondContractWebView(
    modifier: Modifier = Modifier,
    content: String,
) {
    val context = LocalContext.current

    AndroidView(
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
                webViewClient = LocalContentWebViewClient(
                    assetLoader = WebViewAssetLoader.Builder()
                        .addPathHandler(
                            "/res/",
                            WebViewAssetLoader.ResourcesPathHandler(context)
                        )
                        .build()
                )
            }
        },
        modifier = modifier,
        update = {
            it.loadDataWithBaseURL(
                null,
                content,
                "text/html",
                "UTF-8",
                null
            )
        })
}

@Preview(showBackground = true)
@Composable
fun SecondContractSignatureScreenPreview() {
    SecondContractReceiverSignatureScreen(
        onBackButtonClick = {},
        onDrawSignatureButtonClick = {},
        onConfirmButtonClick = {},
        confirmed = false
    )
}