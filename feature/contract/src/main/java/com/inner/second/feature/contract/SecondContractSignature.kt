package com.inner.second.feature.contract

import android.view.ViewGroup
import android.webkit.WebSettings
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.webkit.WebViewAssetLoader
import com.inner.second.core.designsystem.SecondActionButton
import com.inner.second.core.designsystem.SecondSignatureBottomSheet
import com.inner.second.core.designsystem.SecondToolbar
import com.inner.second.core.designsystem.theme.Background
import com.inner.second.core.model.ContractType
import com.inner.second.feature.contract.preview.ContractWebViewParameterProvider

@Composable
fun SecondContractSignatureRoute(
    newContractViewModel: NewContractViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit,
    navigateToContractSend: () -> Unit,
) {
    val contractType by newContractViewModel.contractType.collectAsStateWithLifecycle()
    val contractForm by newContractViewModel.contractForm.collectAsStateWithLifecycle()

    val sheetState = rememberStandardBottomSheetState(
        skipHiddenState = false
    )
    var showSignatureBottomSheet by remember { mutableStateOf(false) }
    var signatureCompleted by remember { mutableStateOf(false) }

    /*val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument(mimeType = "application/pdf")
    ) {
        it?.let { newContractViewModel.saveContract(uri = it) }
    }*/

    SecondContractSignatureScreen(
        toolbarTitle = "${contractType.getContractName()} 만들기",
        contractFormByHtml = contractForm,
        signatureCompleted = signatureCompleted,
        onBackButtonClick = onBackButtonClick,
        onDrawSignatureButtonClick = {
            showSignatureBottomSheet = true
        },
        onSendButtonClick = {
            navigateToContractSend()
        }
    )

    if (showSignatureBottomSheet) {
        SecondSignatureBottomSheet(
            onDrawSignatureComplete = {
                newContractViewModel.onSignatureComplete(bitmap = it)
                signatureCompleted = true
            },
            onDismiss = { showSignatureBottomSheet = false },
            sheetState = sheetState,
        )
    }
}

@Composable
fun SecondContractSignatureScreen(
    toolbarTitle: String,
    contractFormByHtml: String,
    signatureCompleted: Boolean,
    onBackButtonClick: () -> Unit,
    onDrawSignatureButtonClick: () -> Unit,
    onSendButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(Background)
    ) {
        SecondToolbar(
            title = toolbarTitle,
            onBackButtonClick = { onBackButtonClick() }
        )
        SecondContractWebView(
            modifier = Modifier.weight(1f),
            content = contractFormByHtml
        )
        SecondActionButton(
            text = "1단계 : 서명하기",
            enabled = !signatureCompleted,
            onButtonClick = {
                onDrawSignatureButtonClick()
            }
        )
        SecondActionButton(
            text = "2단계 : 계약서 전송하기",
            enabled = signatureCompleted,
            onButtonClick = {
                onSendButtonClick()
            }
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
fun SecondContractSignatureScreenPreview(
    @PreviewParameter(ContractWebViewParameterProvider::class) contractFormByHtml: String,
) {
    SecondContractSignatureScreen(
        toolbarTitle = "${ContractType.IOU.getContractName()} 만들기",
        contractFormByHtml = contractFormByHtml,
        signatureCompleted = false,
        onBackButtonClick = {},
        onDrawSignatureButtonClick = {},
        onSendButtonClick = {},
    )
}