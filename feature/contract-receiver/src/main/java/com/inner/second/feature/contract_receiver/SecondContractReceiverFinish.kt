package com.inner.second.feature.contract_receiver

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inner.second.core.designsystem.SecondActionButton
import com.inner.second.core.designsystem.theme.Background

@Composable
fun SecondContractReceiverFinishRoute(
    popBackStack: () -> Unit,
    navigateToDetail: (Int) -> Unit,
) {
    SecondContractReceiverFinishScreen(
        onFinishButtonClick = { popBackStack() },
        onDetailButtonClick = { navigateToDetail(0) }
    )
}

@Composable
fun SecondContractReceiverFinishScreen(
    onFinishButtonClick: () -> Unit,
    onDetailButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "\uD83C\uDF89 계약서 서명 완료! ",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "계약서 작성이 완료됐어요.\n"
                        + "해당 계약서가 만료되기 전에 알림으로 알려드릴게요.",
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            SecondActionButton(
                modifier = Modifier.weight(1f),
                text = "완료",
                onButtonClick = { onFinishButtonClick() }
            )
            VerticalDivider(
                thickness = 1.dp,
                color = Color.White
            )
            SecondActionButton(
                modifier = Modifier.weight(1f),
                text = "상세 화면으로 이동",
                onButtonClick = { onDetailButtonClick() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondContractReceiverFinishScreenPreview() {
    SecondContractReceiverFinishScreen(
        onFinishButtonClick = {},
        onDetailButtonClick = {}
    )
}