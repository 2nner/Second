package com.inner.second.core.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDate.toFormattedString(): String = this.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
fun LocalDateTime.toFormattedString(): String = this.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))