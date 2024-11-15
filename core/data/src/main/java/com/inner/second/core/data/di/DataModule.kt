package com.inner.second.core.data.di

import com.inner.second.core.data.user.ContractRepository
import com.inner.second.core.data.user.ContractRepositoryImpl
import com.inner.second.core.data.user.FakeUserRepository
import com.inner.second.core.data.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindUserRepository(userRepository: FakeUserRepository) : UserRepository

    @Binds
    fun bindContractRepository(contractRepository: ContractRepositoryImpl) : ContractRepository
}