package com.inner.second.core.model

import androidx.compose.runtime.Stable
import com.inner.second.core.util.toFormattedString
import java.time.LocalDateTime

@Stable
data class DateDuration(
    val startDate: LocalDateTime = LocalDateTime.now(),
    val endDate: LocalDateTime = startDate.plusYears(1),
) {
    fun display(): String = "${startDate.toFormattedString()} ~ ${endDate.toFormattedString()}"
}