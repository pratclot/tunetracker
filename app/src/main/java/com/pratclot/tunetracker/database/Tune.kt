package com.pratclot.tunetracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tunes")
data class Tune(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "tune_name")
    val tuneName: String,

    @ColumnInfo(name = "tune_key")
    val tuneKey: String = "C"
)
