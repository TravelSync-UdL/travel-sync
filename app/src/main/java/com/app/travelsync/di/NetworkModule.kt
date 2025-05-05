package com.app.travelsync.di

import com.app.travelsync.data.remote.api.HotelApiService
import com.app.travelsync.data.repository.HotelRepositoryImpl
import com.app.travelsync.domain.repository.HotelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://13.39.162.212/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .build()


    @Provides
    @Singleton
    fun provideBookRepository(api: HotelApiService): HotelRepository =
        HotelRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideBookApi(retrofit: Retrofit): HotelApiService =
        retrofit.create(HotelApiService::class.java)
}