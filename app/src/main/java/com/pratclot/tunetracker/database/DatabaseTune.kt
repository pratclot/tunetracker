package com.pratclot.tunetracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pratclot.tunetracker.domain.Tune

@Entity
data class DatabaseTune(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "tab_web_url")
    val tabWebUrl: String,

    @ColumnInfo(name = "tab_local_url")
    val tabLocalUrl: String,

    @ColumnInfo(name = "download_complete")
    val downloadComplete: Boolean
) {
    fun asDomainModel(): Tune {
        return Tune(
            id = id,
            name = name,
            tabWebUrl = tabWebUrl,
            tabLocalUrl = tabLocalUrl,
            downloadComplete = downloadComplete
        )
    }
}

fun List<DatabaseTune>.asDomainModel(): List<Tune> {
    return map {
        Tune(
            id = it.id,
            name = it.name,
            tabWebUrl = it.tabWebUrl,
            tabLocalUrl = it.tabLocalUrl,
            downloadComplete = it.downloadComplete
        )
    }
}
