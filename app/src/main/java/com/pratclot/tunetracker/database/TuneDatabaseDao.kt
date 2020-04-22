package com.pratclot.tunetracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TuneDatabaseDao {
    @Insert
    fun insert(tune: Tune)

    @Update
    fun update(tune: Tune)

    @Query("SELECT * FROM tunes WHERE tune_name = :name")
    fun get(name: String): Tune?

    @Query("SELECT * FROM tunes")
    fun getAll(): LiveData<List<Tune>>

    @Query("DELETE FROM tunes")
    fun clear()
}
