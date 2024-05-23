package com.airsofter.airsoftermobile.core.di

import com.airsofter.airsoftermobile.gameDetail.data.network.IGameDetailClient
import com.airsofter.airsoftermobile.gameList.data.network.IGameListClient
import com.airsofter.airsoftermobile.login.data.network.ILoginClient
import com.airsofter.airsoftermobile.register.data.network.IRegisterClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://airsofterapi20240521182128.azurewebsites.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideLoginClient(retrofit: Retrofit):ILoginClient{
        return retrofit.create(ILoginClient::class.java)
    }

    @Singleton
    @Provides
    fun provideRegisterClient(retrofit: Retrofit):IRegisterClient{
        return retrofit.create(IRegisterClient::class.java)
    }

    @Singleton
    @Provides
    fun provideGameListClient(retrofit: Retrofit):IGameListClient{
        return retrofit.create(IGameListClient::class.java)
    }

    @Singleton
    @Provides
    fun provideGameDetailClient(retrofit: Retrofit): IGameDetailClient {
        return retrofit.create(IGameDetailClient::class.java)
    }
}