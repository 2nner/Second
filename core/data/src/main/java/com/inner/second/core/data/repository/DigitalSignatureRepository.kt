package com.inner.second.core.data.repository

import java.io.File

interface DigitalSignatureRepository {

    fun applySignature(file: File): File
}