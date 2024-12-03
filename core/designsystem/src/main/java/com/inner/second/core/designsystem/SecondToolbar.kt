package com.inner.second.core.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inner.second.core.designsystem.theme.Background

@Composable
fun SecondToolbar(
    title: String,
    enableLtr: Boolean = false,
    onBackButtonClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .background(Background),
    ) {
        Box(
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                textAlign = if (enableLtr) TextAlign.Start else TextAlign.Center,
                maxLines = 1,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            if (onBackButtonClick != null) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(
                            indication = null,
                            interactionSource = null
                        ) { onBackButtonClick() },
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "backButton",
                    tint = Color.Black,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondToolbarPreview() {
    SecondToolbar(
        title = "세컨드",
        onBackButtonClick = {}
    )
}