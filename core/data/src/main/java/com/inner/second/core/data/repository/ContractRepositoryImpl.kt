package com.inner.second.core.data.repository

import android.content.ContentResolver
import android.net.Uri
import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import com.itextpdf.io.font.FontProgramFactory
import com.itextpdf.layout.font.FontProvider
import java.io.File
import javax.inject.Inject

class ContractRepositoryImpl @Inject constructor(
    private val contentResolver: ContentResolver,
    private val digitalSignatureRepository: DigitalSignatureRepository,
) : ContractRepository {

    override fun exportHtmlToPdf(
        title: String,
        html: String,
    ): File {
        val file = File.createTempFile(title, ".pdf")
        val fontProvider = FontProvider().apply {
            addFont(
                FontProgramFactory.createFont(
                    "assets/font/nanumgothic.ttf"
                )
            )
            addFont(
                FontProgramFactory.createFont(
                    "assets/font/nanumgothicbold.ttf"
                )
            )
        }
        file.outputStream().use { outputStream ->
            HtmlConverter.convertToPdf(
                html,
                outputStream,
                ConverterProperties().setFontProvider(fontProvider)
            )
        }

        val signatureFile = digitalSignatureRepository.applySignature(file)
        return signatureFile
    }

    override fun saveContract(
        file: File,
        uri: Uri,
    ) {
        val outputStream = contentResolver.openOutputStream(uri, "w")
        outputStream?.use {
            it.write(file.readBytes())
        }
        outputStream?.close()
    }
}