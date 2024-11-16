package com.inner.second.core.data.di

import com.inner.second.core.data.repository.ContractRepository
import com.inner.second.core.data.repository.ContractRepositoryImpl
import com.inner.second.core.data.repository.DigitalSignatureRepository
import com.inner.second.core.data.repository.DigitalSignatureRepositoryImpl
import com.inner.second.core.data.repository.FakeUserRepository
import com.inner.second.core.data.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindUserRepository(userRepository: FakeUserRepository): UserRepository

    @Binds
    fun bindContractRepository(contractRepository: ContractRepositoryImpl): ContractRepository

    @Binds
    fun bindDigitalSignatureRepository(
        digitalSignatureRepository: DigitalSignatureRepositoryImpl,
    ): DigitalSignatureRepository
}