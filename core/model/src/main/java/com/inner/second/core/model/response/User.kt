package com.inner.second.core.model.response

data class User(
    val name: String,
    val englishName: String,
    val birthday: String,
    val phoneNumber: String,
    val address: String,
) {

    companion object {
        fun dummy() = User(
            name = "김한슬",
            englishName = "Kim Hanseul",
            birthday = "2000.09.19",
            phoneNumber = "010-1234-5678",
            address = "서울특별시 동작구 상도로 369 정보과학관 옥상",
        )
    }
}