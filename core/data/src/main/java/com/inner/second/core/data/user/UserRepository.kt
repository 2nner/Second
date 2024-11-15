package com.inner.second.core.data.user

import com.inner.second.core.model.response.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun fetchUser(): Flow<User>
}