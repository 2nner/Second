package com.inner.second.feature.contract

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.inner.second.core.designsystem.R
import com.inner.second.core.designsystem.SecondActionButton
import com.inner.second.core.designsystem.theme.Background
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SecondContractFinishRoute(
    popBackStack: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    context: Context = LocalContext.current
) {
    SecondContractFinishScreen(
        onFinishButtonClick = { popBackStack() },
        onDetailButtonClick = { navigateToDetail(0) }
    )

    LaunchedEffect(Unit) {
        val notificationManager = NotificationManagerCompat.from(context)
        val notification = NotificationCompat.Builder(context, "ContractDueDateReminder")
            .setSmallIcon(R.mipmap.ic_app_logo)
            .setContentText("계약서 알림 리마인더")
            .setContentText("계약서 서명 만료일까지 일주일도 남지 않았어요.\n서둘러 확인해주세요! (D-6)")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(Random.nextInt(), notification)
        }
    }
}

@Composable
fun SecondContractFinishScreen(
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
                text = "\uD83C\uDF89 계약서 작성 완료! ",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "상대방이 서명하면 모든 과정이 완료돼요.\n" +
                        "일주일 안으로 계약이 성립되지 않으면, 계약서는 자동 파기되니 주의해주세요.",
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
fun SecondContractFinishScreenPreview() {
    SecondContractFinishScreen(
        onFinishButtonClick = {},
        onDetailButtonClick = {}
    )
}