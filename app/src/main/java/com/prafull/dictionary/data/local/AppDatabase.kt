package com.prafull.dictionary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prafull.dictionary.utils.WordsInfoTypeConverter

@Database(entities = [HistoryEntity::class], version = 1, exportSchema = false)
@TypeConverters(WordsInfoTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao(): AppDao

}