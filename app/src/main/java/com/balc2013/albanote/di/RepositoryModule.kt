package com.balc2013.albanote.di

import com.balc2013.albanote.api.MemberApi
import com.balc2013.albanote.etc.LocalDBManager
import com.balc2013.albanote.repository.MemberRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun memberRemoteRepository(api: MemberApi, localDBManager: LocalDBManager) =
        MemberRepository(api, localDBManager)

}