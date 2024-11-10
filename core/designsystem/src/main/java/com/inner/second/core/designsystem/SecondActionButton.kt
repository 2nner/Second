package com.inner.second.core.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.inner.second.core.designsystem.theme.ActionButtonBackground

@Composable
fun SecondActionButton(
    text: String,
    onButtonClick: () -> Unit,
) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(indication = null, interactionSource = null) { onButtonClick() }
            .background(ActionButtonBackground)
            .padding(vertical = 16.dp),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        color = Color.White
    )
}

@Preview(showBackground = true)
@Composable
fun SecondActionButtonPreview() {
    SecondActionButton(
        text = "다음",
        onButtonClick = {}
    )
}