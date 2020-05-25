package com.pratclot.tunetracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.pratclot.tunetracker.database.partialentities.DatabaseTuneFilePath

@Dao
interface TuneDatabaseDao {
    @Insert
    fun insert(databaseTune: DatabaseTune)

    @Update()
    fun update(databaseTune: DatabaseTune)

    @Update(entity = DatabaseTune::class)
    fun updateFilePath(databaseTuneFilePath: DatabaseTuneFilePath)

    @Query("SELECT * FROM databasetune WHERE name = :name")
    fun get(name: String): DatabaseTune?

    @Query("SELECT *FROM databasetune WHERE id = :id")
    fun getById(id: Long): DatabaseTune

    @Query("SELECT * FROM databasetune")
    fun getAll(): LiveData<List<DatabaseTune>>

    @Query("DELETE FROM databasetune")
    fun clear()
}
