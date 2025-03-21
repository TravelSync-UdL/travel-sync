package com.app.travelsync

import android.content.Context
import android.content.SharedPreferences
import com.app.travelsync.data.SharedPrefsManager
import com.app.travelsync.domain.repository.TripRepository
import com.app.travelsync.domain.repository.TripRepositorylmpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences =
        context.getSharedPreferences("${BuildConfig.APPLICATION_ID}_preferences", Context.MODE_PRIVATE)


    @Provides
    @Singleton
    fun provideSharedPrefsManager(
        sharedPreferences: SharedPreferences,
        @ApplicationContext context: Context
    ): SharedPrefsManager =
        SharedPrefsManager(sharedPreferences, context)

    @Provides
    @Singleton
    fun provideTaskRepository(): TripRepository = TripRepositorylmpl()
}
