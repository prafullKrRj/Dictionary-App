package com.prafullkumar.dictionary.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.prafullkumar.dictionary.data.local.AppDao
import com.prafullkumar.dictionary.data.local.AppDatabase
import com.prafullkumar.dictionary.data.remote.DictionaryApiService
import com.prafullkumar.dictionary.data.repositories.HistoryRepositoryImpl
import com.prafullkumar.dictionary.data.repositories.HomeRepositoryImpl
import com.prafullkumar.dictionary.domain.repositories.HistoryRepository
import com.prafullkumar.dictionary.domain.repositories.HomeRepository
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
