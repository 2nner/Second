package com.inner.second.core.model

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.runtime.Stable
import androidx.core.os.BundleCompat
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Parcelize
@Serializable
@Stable
sealed interface ContractType : Parcelable {

    /** 직접 계약서 만들기 */
    @Parcelize
    @Serializable
    data object New : ContractType

    /** 차용증 */
    @Parcelize
    @Serializable
    data object IOU : ContractType

    fun getContractName(): String = when (this) {
        is New -> "직접 계약서"
        is IOU -> "차용증"
    }

    fun getContractForm(): ContractForm = when (this) {
        is New -> ContractForm.New
        is IOU -> ContractForm.IOU
    }

    companion object {
        val navType =
            object : NavType<ContractType>(isNullableAllowed = false) {
                override fun get(bundle: Bundle, key: String): ContractType? {
                    return BundleCompat.getParcelable(
                        bundle,
                        key,
                        ContractType::class.java
                    )
                }

                override fun parseValue(value: String): ContractType {
                    return Json.decodeFromString(serializer(), value)
                }

                override fun put(bundle: Bundle, key: String, value: ContractType) {
                    bundle.putParcelable(key, value)
                }

                override fun serializeAsValue(value: ContractType): String {
                    return Uri.encode(
                        Json.encodeToString(
                            serializer(),
                            value
                        )
                    )
                }
            }
    }
}
