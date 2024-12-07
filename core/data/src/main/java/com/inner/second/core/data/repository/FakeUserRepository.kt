package com.inner.second.core.data.repository

import com.inner.second.core.model.response.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeUserRepository @Inject constructor(

) : UserRepository {

    override fun fetchUser(): Flow<User> = flowOf(User.dummy())
}