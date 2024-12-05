package com.inner.feature.contract_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.inner.second.core.designsystem.R
import com.inner.second.core.designsystem.SecondButton
import com.inner.second.core.designsystem.SecondToolbar
import com.inner.second.core.designsystem.theme.ActionButtonBackground
import com.inner.second.core.designsystem.theme.ActionButtonDisabledBackground
import com.inner.second.core.designsystem.theme.Background
import com.inner.second.core.designsystem.theme.Purple
import com.inner.second.core.model.Contract

@Composable
fun SecondContractDetailRoute(
    viewModel: ContractDetailViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit,
) {
    val contract by viewModel.contract.collectAsStateWithLifecycle()

    SecondContractDetailScreen(
        onBackButtonClick = onBackButtonClick,
        contract = contract
    )
}

@Composable
fun SecondContractDetailScreen(
    onBackButtonClick: () -> Unit,
    contract: Contract?,
) {
    if (contract == null) {
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(Background)
    ) {
        SecondToolbar(
            title = "계약서 상세",
            onBackButtonClick = onBackButtonClick,
        )
        SecondContractDetailContent(
            contract = contract
        )
    }
}

@Composable
fun SecondContractDetailContent(
    contract: Contract,
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        SecondContractDetailHeader(contract)
        HorizontalDivider()
        SecondContractDetailButtons(contract)
    }
}

@Composable
fun SecondContractDetailHeader(
    contract: Contract,
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        contract.dueDate?.let { dueDate ->
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = dueDate,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Purple
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = contract.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "계약서 유형 : ${contract.type.getContractName()}",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = ActionButtonBackground,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = contract.duration.display(),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = ActionButtonDisabledBackground,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "채권자 : ${contract.name}",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = ActionButtonDisabledBackground,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "채무자 : ${contract.oppositeName}",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = ActionButtonDisabledBackground,
            )
            contract.description?.let { description ->
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = description,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ActionButtonDisabledBackground,
                )
            }
        }
    }
}

@Composable
fun SecondContractDetailButtons(
    contract: Contract,
) {
    var alarmEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 16.dp)
    ) {
        when {
            contract.isMine && !contract.contractConcluded -> {
                SecondButton(
                    trailingIconRes = R.drawable.ic_chevron_right,
                    text = "계약서 보기"
                )
                SecondButton(
                    trailingIconRes = R.drawable.ic_chevron_right,
                    text = "계약서 전송하기"
                )
                SecondButton(
                    trailingIconRes = R.drawable.ic_chevron_right,
                    text = "계약서 삭제하기"
                )
            }

            !contract.isMine && !contract.contractConcluded -> {
                SecondButton(
                    trailingIconRes = R.drawable.ic_chevron_right,
                    text = "계약서 확인 후 서명하기"
                )
            }

            else -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(Color.White)
                        .padding(12.dp)
                        .clickable(indication = null, interactionSource = null) { },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "계약 기간 종료 알림",
                        modifier = Modifier.weight(1f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                    )
                    Switch(
                        modifier = Modifier.scale(0.9f),
                        checked = alarmEnabled,
                        onCheckedChange = { alarmEnabled = !alarmEnabled },
                        colors = SwitchDefaults.colors(
                            uncheckedThumbColor = Color.White,
                            uncheckedBorderColor = Color.Gray,
                            uncheckedTrackColor = Color.LightGray,
                            checkedThumbColor = Color.White,
                            checkedBorderColor = Color(0xFF34C759),
                            checkedTrackColor = Color(0xFF34C759)
                        )
                    )
                }
                SecondButton(
                    trailingIconRes = R.drawable.ic_chevron_right,
                    text = "계약서 보기"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondContractDetailScreenPreview() {
    SecondContractDetailScreen(
        onBackButtonClick = {},
        contract = Contract.dummy().copy(description = "아이패드")
    )
}