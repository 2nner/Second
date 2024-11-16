package com.inner.second.core.data.repository

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import java.io.File
import java.security.KeyPairGenerator
import java.security.Signature
import javax.inject.Inject

class DigitalSignatureRepositoryImpl @Inject constructor() : DigitalSignatureRepository {

    /**
     * fixme : 메인 스레드에서 실행되지 않도록 수정 필요
     * @see https://developer.android.com/privacy-and-security/keystore#UsingAndroidKeyStore
     */
    override fun applySignature(file: File): File {
        // ECDSA 알고리즘을 사용한 키 쌍 생성기 추가
        val kpg = KeyPairGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_EC,
            "AndroidKeyStore"
        )
        // 키 쌍 생성기 초기화
        val parameterSpec = KeyGenParameterSpec.Builder(
            "alias",
            KeyProperties.PURPOSE_SIGN
        ).run {
            setDigests(KeyProperties.DIGEST_SHA256)
            build()
        }
        kpg.initialize(parameterSpec)

        // 키 쌍 생성
        val entry = kpg.generateKeyPair()

        // 서명 생성
        val signatureByte = Signature.getInstance("SHA256withECDSA").run {
            initSign(entry.private)
            update(file.readBytes())
            sign()
        }

        // 서명 검증
        Signature.getInstance("SHA256withECDSA").run {
            initVerify(entry.public)
            update(file.readBytes())
            val result = verify(signatureByte)
            Log.d("TAG", result.toString())
        }

        // 서명 추가
        file.appendBytes(signatureByte)

        return file
    }
}