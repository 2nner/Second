package com.inner.second.feature.contract

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inner.second.core.designsystem.SecondActionButton
import com.inner.second.core.designsystem.SecondToolbar
import com.inner.second.core.designsystem.theme.Background
import com.inner.second.core.designsystem.theme.TextFieldPlaceHolder
import com.inner.second.core.designsystem.theme.TextFieldPurple

@Composable
fun SecondContractSendRoute(
    onBackButtonClick: () -> Unit,
    navigateToFinish: () -> Unit,
) {
    SecondContractSendScreen(
        onBackButtonClick = onBackButtonClick,
        onSendButtonClick = {
            navigateToFinish()
        }
    )
}

@Composable
fun SecondContractSendScreen(
    onBackButtonClick: () -> Unit,
    onSendButtonClick: (String) -> Unit,
) {
    var opponentId by remember { mutableStateOf("") }
    val actionButtonText = remember(opponentId) {
        if (opponentId.isEmpty()) "넘어가기" else "전송하기"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(Background)
    ) {
        SecondToolbar(
            title = "계약서 전송하기",
            onBackButtonClick = { onBackButtonClick() }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SecondContractSendContent(
                modifier = Modifier.weight(1f),
                opponentId = opponentId,
                onOpponentIdChange = { opponentId = it }
            )
            SecondActionButton(
                text = actionButtonText,
                onButtonClick = { onSendButtonClick(opponentId) }
            )
        }
    }
}

@Composable
fun SecondContractSendContent(
    modifier: Modifier = Modifier,
    opponentId: String,
    onOpponentIdChange: (String) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            text = "계약서를 전송할 상대방의 ID를 입력해주세요.\n" +
                    "(생략 가능)",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(12.dp))
        BasicTextField(
            value = opponentId,
            onValueChange = {
                onOpponentIdChange(it)
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            decorationBox = @Composable { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = opponentId,
                    innerTextField = innerTextField,
                    enabled = true,
                    singleLine = false,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = TextFieldPurple,
                        unfocusedBorderColor = TextFieldPurple,
                    ),
                    placeholder = {
                        Text(
                            text = "ID를 입력해주세요.",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextFieldPlaceHolder
                        )
                    }
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SecondContractSendScreenPreview() {
    SecondContractSendScreen(
        onBackButtonClick = {},
        onSendButtonClick = {},
    )
}