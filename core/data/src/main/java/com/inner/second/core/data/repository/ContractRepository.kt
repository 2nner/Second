package com.inner.second.core.data.repository

import android.net.Uri
import java.io.File

interface ContractRepository {

    fun exportHtmlToPdf(
        title: String,
        html: String
    ): File

    fun saveContract(
        file: File,
        uri: Uri,
    )
}