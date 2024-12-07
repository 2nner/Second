package com.inner.second.core.data.di

import com.inner.second.core.data.repository.ContractDetailRepository
import com.inner.second.core.data.repository.ContractRepository
import com.inner.second.core.data.repository.ContractRepositoryImpl
import com.inner.second.core.data.repository.DigitalSignatureRepository
import com.inner.second.core.data.repository.DigitalSignatureRepositoryImpl
import com.inner.second.core.data.repository.FakeContractDetailRepository
import com.inner.second.core.data.repository.FakeHomeRepository
import com.inner.second.core.data.repository.FakeUserRepository
import com.inner.second.core.data.repository.HomeRepository
import com.inner.second.core.data.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindUserRepository(userRepository: FakeUserRepository): UserRepository

    @Singleton
    @Binds
    fun bindContractRepository(contractRepository: ContractRepositoryImpl): ContractRepository

    @Singleton
    @Binds
    fun bindDigitalSignatureRepository(
        digitalSignatureRepository: DigitalSignatureRepositoryImpl,
    ): DigitalSignatureRepository

    @Singleton
    @Binds
    fun bindHomeRepository(homeRepository: FakeHomeRepository): HomeRepository

    @Singleton
    @Binds
    fun bindContractDetailRepository(contractDetailRepository: FakeContractDetailRepository): ContractDetailRepository
}