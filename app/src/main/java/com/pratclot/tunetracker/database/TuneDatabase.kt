package com.pratclot.tunetracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [DatabaseTune::class], version = 4, exportSchema = false)
abstract class TuneDatabase : RoomDatabase() {

    abstract val tuneDatabaseDao: TuneDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: TuneDatabase? = null

        fun getInstance(context: Context): TuneDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = databaseBuilder(
                        context.applicationContext,
                        TuneDatabase::class.java,
                        "tune_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
