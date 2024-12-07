package com.inner.second.core.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

fun LocalDate.toFormattedString(): String = this.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
fun LocalDateTime.toFormattedString(): String =
    this.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))

fun LocalDateTime.toFormattedStringInKorean(): String =
    this.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))

fun LocalDateTime.toFormattedStringInKorean2(): String {
    return this.format(DateTimeFormatter.ofPattern("MM.dd E a HH:mm").withLocale(Locale.KOREAN))
}