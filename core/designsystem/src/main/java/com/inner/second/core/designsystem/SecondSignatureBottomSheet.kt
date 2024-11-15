package com.inner.second.core.designsystem

import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.joelkanyi.sain.Sain
import io.github.joelkanyi.sain.SignatureState

@Composable
fun SecondSignatureBottomSheet(
    onDrawSignatureComplete: (ImageBitmap) -> Unit,
    onDismiss: () -> Unit,
    sheetState: SheetState,
) {
    val state = remember { SignatureState() }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Text(
                text = "서명하기",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Sain(
                state = state,
                onComplete = {},
                modifier = Modifier.fillMaxWidth()
                    .height(200.dp)
                    .background(Color.White),
                actions = {}
            )
            Spacer(modifier = Modifier.height(12.dp))
            SecondActionButton(
                text = "완료",
                onButtonClick = {
                    state.signature?.let(onDrawSignatureComplete)
                    onDismiss()
                }
            )
        }
    }
}

@Composable
@Preview
fun SecondSignatureBottomSheetPreview() {
    SecondSignatureBottomSheet(
        onDrawSignatureComplete = {},
        onDismiss = {},
        sheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Expanded
        )
    )
}