package com.inner.second.core.data.user

import com.inner.second.core.model.response.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeUserRepository @Inject constructor(

) : UserRepository {

    override fun fetchUser(): Flow<User> = flowOf(
        User(
            name = "김한슬",
            address = "서울 동작구 사당로 50 (상도동, 숭실대학교 정보과학관) 910호",
            phoneNumber = "010-5752-3665"
        )
    )
}