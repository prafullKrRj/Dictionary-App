package com.prafull.dictionary.di

import android.app.Application
import androidx.room.Room
import com.prafull.dictionary.data.local.AppDao
import com.prafull.dictionary.data.local.AppDatabase
import com.prafull.dictionary.data.remote.DictionaryApiService
import com.prafull.dictionary.data.repositories.HistoryRepositoryImpl
import com.prafull.dictionary.data.repositories.HomeRepositoryImpl
import com.prafull.dictionary.domain.repositories.HistoryRepository
import com.prafull.dictionary.domain.repositories.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDictionaryApi(): DictionaryApiService {
        return Retrofit
            .Builder()
            .baseUrl("https://api.dictionaryapi.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApiService::class.java)
    }
    /*@Provides
    @Singleton
    fun provideContext(application: Application): Application {
        return application
    }*/
    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
    @Provides
    @Singleton
    fun provideAppDao(appDatabase: AppDatabase): AppDao {
        return appDatabase.appDao()
    }
    @Provides
    fun provideHomeRepository(dictionaryApiService: DictionaryApiService, appDao: AppDao): HomeRepository {
        return HomeRepositoryImpl(
            dictionaryApiService,
            appDao
        )
    }
    @Provides
    fun provideHistoryRepository(appDao: AppDao): HistoryRepository {
        return HistoryRepositoryImpl(
            appDao
        )
    }
}
