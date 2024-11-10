package com.inner.second.core.util

fun String.formattedToLong(): Long = this.replace(",", "").toLong()