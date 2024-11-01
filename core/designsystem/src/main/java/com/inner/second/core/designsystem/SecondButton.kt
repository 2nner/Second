package com.inner.second.core.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SecondButton(
    titleIconRes: Int,
    trailingIconRes: Int? = null,
    titleIconTintColor: Color? = null,
    trailingIconTintColor: Color? = null,
    text: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(12.dp)
            .clickable(indication = null, interactionSource = null) { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = titleIconRes),
            tint = titleIconTintColor ?: LocalContentColor.current,
            contentDescription = "titleIcon"
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
        )

        if (trailingIconRes != null) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = trailingIconRes),
                tint = trailingIconTintColor ?: LocalContentColor.current,
                contentDescription = "trailingIcon"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondButtonPreview() {
    SecondButton(
        titleIconRes = R.drawable.ic_edit_outlined,
        trailingIconRes = R.drawable.ic_chevron_right,
        text = "직접 작성하기",
    )
}