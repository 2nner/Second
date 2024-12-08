package com.inner.second.core.designsystem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SecondTextBottomSheet(
    text: String,
    onDismiss: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    sheetState: SheetState,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(12.dp))
            SecondActionButton(
                text = "완료",
                onButtonClick = {
                    onConfirmButtonClick()
                    onDismiss()
                }
            )
        }
    }
}

@Composable
@Preview
fun SecondTextBottomSheetPreview() {
    SecondTextBottomSheet(
        text = "본인은 상기 계약서의 내용을 확인하였으며, 여기에 서명하는 것에 동의합니다.",
        onDismiss = {},
        onConfirmButtonClick = {},
        sheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Expanded
        ),
    )
}