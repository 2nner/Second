package com.inner.second.core.util

import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import java.io.ByteArrayOutputStream

fun ImageBitmap.toEncodedString(): String {
    val byteArray = ByteArrayOutputStream().run {
        use {
            asAndroidBitmap().compress(android.graphics.Bitmap.CompressFormat.PNG, 100, it)
        }
        close()

        return@run toByteArray()
    }
    return Base64.encodeToString(byteArray, 0);
}