package com.inner.second.feature.contract

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.inner.second.core.designsystem.SecondActionButton
import com.inner.second.core.designsystem.SecondToolbar
import com.inner.second.core.designsystem.theme.Background
import com.inner.second.core.model.ContractType
import com.inner.second.feature.contract.input.ContractInputItem

@Composable
fun SecondContractGetInfoRoute(
    newContractViewModel: NewContractViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit,
    onNextActionButtonClick: () -> Unit,
) {
    val contractType by newContractViewModel.contractType.collectAsStateWithLifecycle()

    SecondContractGetInfoScreen(
        contractType = contractType,
        onBackButtonClick = onBackButtonClick,
        onContractFormInputChange = newContractViewModel::onContractFormChange,
        onNextActionButtonClick = onNextActionButtonClick,
    )
}

@Composable
fun SecondContractGetInfoScreen(
    contractType: ContractType,
    onBackButtonClick: () -> Unit,
    onNextActionButtonClick: () -> Unit,
    onContractFormInputChange: (String, Any) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(Background)
    ) {
        SecondToolbar(
            title = "${contractType.getContractName()} 만들기",
            onBackButtonClick = { onBackButtonClick() }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            SecondContractGetInfoContent(
                modifier = Modifier.weight(1f),
                contractType = contractType,
                onContractFormInputChange = onContractFormInputChange
            )
            SecondActionButton(
                text = "다음",
                onButtonClick = { onNextActionButtonClick() }
            )
        }
    }
}

@Composable
fun SecondContractGetInfoContent(
    modifier: Modifier = Modifier,
    contractType: ContractType,
    onContractFormInputChange: (String, Any) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        contractType.getContractForm().inputs().forEachIndexed { index, formInput ->
            ContractInputItem(
                title = formInput.title,
                inputType = formInput.type,
                onItemChange = { data ->
                    onContractFormInputChange(formInput.key, data)
                }
            )
            if (index != contractType.getContractForm().inputs().lastIndex) {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview
@Composable
fun SecondContractGetInfoScreenPreview() {
    SecondContractGetInfoScreen(
        contractType = ContractType.New,
        onBackButtonClick = {},
        onContractFormInputChange = { _, _ -> },
        onNextActionButtonClick = {}
    )
}