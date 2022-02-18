package com.balc2013.albanote.di

import android.content.Context
import com.balc2013.albanote.api.MemberApi
import com.balc2013.albanote.etc.AppConfig
import com.balc2013.albanote.etc.LocalDBManager
import com.balc2013.albanote.BuildConfig
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideLocalData(@ApplicationContext context: Context) = LocalDBManager(context)

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .connectTimeout(3000L, TimeUnit.MILLISECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient.Builder().build()
    }

    private fun baseRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .addConverterFactory((GsonConverterFactory.create(gson)))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
    }

    private fun baseRetrofitFutureInvestBuilder(okHttpClient: OkHttpClient): Retrofit {
        return baseRetrofitBuilder(okHttpClient)
            .baseUrl(AppConfig.SERVER_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideMemberApi(okHttpClient: OkHttpClient): MemberApi {
        return baseRetrofitFutureInvestBuilder(okHttpClient).create(MemberApi::class.java)
    }
}