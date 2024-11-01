package com.inner.second.core.navigation

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.BundleCompat
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Parcelize
@Serializable
sealed interface ContractType : Parcelable {
    @Parcelize
    @Serializable
    data object New : ContractType

    @Parcelize
    @Serializable
    data object IOU : ContractType

    fun getContractName(): String = when (this) {
        New -> "직접 계약서"
        IOU -> "차용증"
    }

    companion object {
        val navType = object : NavType<ContractType>(isNullableAllowed = false) {
            override fun get(bundle: Bundle, key: String): ContractType? {
                return BundleCompat.getParcelable(bundle, key, ContractType::class.java)
            }

            override fun parseValue(value: String): ContractType {
                return Json.decodeFromString(serializer(), value)
            }

            override fun put(bundle: Bundle, key: String, value: ContractType) {
                bundle.putParcelable(key, value)
            }

            override fun serializeAsValue(value: ContractType): String {
                return Uri.encode(Json.encodeToString(serializer(), value))
            }
        }
    }
}
