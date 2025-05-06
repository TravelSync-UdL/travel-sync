package com.app.travelsync.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.travelsync.BuildConfig
import com.app.travelsync.data.SharedPrefsManager
import com.app.travelsync.domain.repository.TripRepository
import com.app.travelsync.data.repository.TripRepositorylmpl
import com.app.travelsync.data.local.AppDatabase
import com.app.travelsync.data.local.dao.ItineraryDao
import com.app.travelsync.data.local.dao.TripDao
import com.app.travelsync.domain.repository.AuthRepository
import com.app.travelsync.data.repository.AuthRepositoryImpl
import com.app.travelsync.data.local.dao.SessionLogDao
import com.app.travelsync.data.local.dao.UserDao
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Aquí afegeixes la nova columna a la taula
            /*
            database.execSQL("ALTER TABLE trip ADD COLUMN ownerLogin TEXT NOT NULL DEFAULT ''")
            database.execSQL("""
            CREATE TABLE IF NOT EXISTS `users` (
                `login` TEXT NOT NULL,
                `username` TEXT NOT NULL,
                `country` TEXT NOT NULL DEFAULT 'undefined',
                `birthdate` TEXT NOT NULL DEFAULT 'undefined',
                `address` TEXT NOT NULL DEFAULT 'undefined',
                `phone` TEXT NOT NULL DEFAULT 'undefined',
                `acceptReceiveEmails` INTEGER NOT NULL DEFAULT 1,
                PRIMARY KEY(`login`)
            )
        """)
            database.execSQL("""
            CREATE TABLE IF NOT EXISTS `session_log` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `action` TEXT NOT NULL DEFAULT 'undefined',
                `userId` TEXT NOT NULL DEFAULT 'undefined',
                `timestamp` INTEGER NOT NULL DEFAULT 0
            )
        """)

            database.execSQL("""
            CREATE TABLE IF NOT EXISTS `reservation` (
                `id` TEXT NOT NULL,
                `hotelName` TEXT NOT NULL,
                `roomType` TEXT NOT NULL,
                `price` INTEGER NOT NULL,
                `startDate` TEXT NOT NULL,
                `endDate` TEXT NOT NULL,
                `userEmail` TEXT NOT NULL,
                PRIMARY KEY(`id`)
            )
        """)*/

            database.execSQL("ALTER TABLE reservation ADD COLUMN reservationId TEXT NOT NULL DEFAULT 'undefined'")
        }
    }


    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences =
        context.getSharedPreferences("${BuildConfig.APPLICATION_ID}_preferences", Context.MODE_PRIVATE)

    // context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE) //bad implementation

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
        ).addMigrations(MIGRATION_3_4).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideTripDao(db: AppDatabase): TripDao = db.tripDao()

    @Provides
    fun provideSItineraryDao(db: AppDatabase): ItineraryDao = db.itineraryDao()

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    fun provideSessionLog(db: AppDatabase): SessionLogDao = db.sessionLogDao()

    @Provides
    @Singleton
    fun provideTripRepository(tripDao: TripDao, itineraryDao: ItineraryDao): TripRepository = TripRepositorylmpl(tripDao, itineraryDao)

    @Provides
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }



}
