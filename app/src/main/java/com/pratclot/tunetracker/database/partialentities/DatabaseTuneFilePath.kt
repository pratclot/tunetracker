package com.pratclot.tunetracker.database.partialentities

import androidx.room.ColumnInfo

data class DatabaseTuneFilePath(
    var id: Long,
    @ColumnInfo(name = "tab_local_url")
    val tabLocalUrl: String
)
