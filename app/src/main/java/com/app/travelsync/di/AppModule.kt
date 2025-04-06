package com.app.travelsync.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.app.travelsync.BuildConfig
import com.app.travelsync.data.SharedPrefsManager
import com.app.travelsync.domain.repository.TripRepository
import com.app.travelsync.data.local.AppDatabase
import com.app.travelsync.data.local.dao.ItineraryDao
import com.app.travelsync.data.local.dao.TripDao
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

    /*@Provides
    @Singleton
    fun provideTaskRepository(): TripRepository = TripRepositorylmpl()*/

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "my_database_name"
        ).build()
    }

    @Provides
    fun provideTripDao(db: AppDatabase): TripDao = db.tripDao()

    @Provides
    fun provideSItineraryDao(db: AppDatabase): ItineraryDao = db.itineraryDao()

    @Provides
    @Singleton
    fun provideTripRepository(tripDao: TripDao, itineraryDao: ItineraryDao): TripRepository = TripRepositorylmpl(tripDao, itineraryDao)

}
