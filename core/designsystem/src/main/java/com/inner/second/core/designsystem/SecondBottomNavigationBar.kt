package com.inner.second.core.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.inner.second.core.designsystem.theme.ActionButtonDisabledBackground
import com.inner.second.core.designsystem.theme.DividerColor

@Composable
fun SecondBottomNavigationBar(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    onAddIconButtonClick: () -> Unit,
    onTabClick: (Int) -> Unit,
) {
    val homeButtonTint = if (selectedTabIndex == 0) Color.Black else ActionButtonDisabledBackground
    val profileButtonTint =
        if (selectedTabIndex == 1) Color.Black else ActionButtonDisabledBackground

    Column(
        modifier = modifier.fillMaxWidth()
            .background(color = Color.White)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = DividerColor),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(58.dp)
                    .clickable(interactionSource = null, indication = null) {
                        onTabClick(0)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(id = R.drawable.ic_home_outlined),
                    tint = homeButtonTint,
                    contentDescription = "home"
                )
            }

            Button(
                modifier = Modifier
                    .size(56.dp)
                    .offset(y = (-8).dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                contentPadding = PaddingValues(0.dp),
                onClick = onAddIconButtonClick,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "iconButton",
                    tint = Color.White
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(58.dp)
                    .clickable(interactionSource = null, indication = null) {
                        onTabClick(1)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(id = R.drawable.ic_person_outlined),
                    tint = profileButtonTint,
                    contentDescription = "home"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondBottomNavigationBarPreview() {
    SecondBottomNavigationBar(
        selectedTabIndex = 0,
        onAddIconButtonClick = {},
        onTabClick = {}
    )
}