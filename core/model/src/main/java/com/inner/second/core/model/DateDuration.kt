package com.inner.second.core.model

import androidx.compose.runtime.Stable
import java.time.LocalDateTime

@Stable
data class DateDuration(
    val startDate: LocalDateTime = LocalDateTime.now(),
    val endDate: LocalDateTime = startDate.plusYears(1),
)