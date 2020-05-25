package com.pratclot.tunetracker.domain

import com.pratclot.tunetracker.database.DatabaseTune
import com.pratclot.tunetracker.database.partialentities.DatabaseTuneFilePath

data class Tune(
    val id: Long? = null,
    var name: String,
    var tabWebUrl: String = "https://tommyemmanuel.files.wordpress.com/2007/09/tommy-emmanuel-windy-and-warm.pdf",
    var tabLocalUrl: String = "file:///",
    var progress: Int = 0,
    var downloadComplete: Boolean = false
) {
    fun asDatabaseModel(): DatabaseTune {
        return when (id) {
            null -> DatabaseTune(
                name = name,
                tabWebUrl = tabWebUrl,
                tabLocalUrl = tabLocalUrl,
                downloadComplete = downloadComplete
            )
            else -> DatabaseTune(
                id = id,
                name = name,
                tabWebUrl = tabWebUrl,
                tabLocalUrl = tabLocalUrl,
                downloadComplete = downloadComplete
            )
        }
    }

    fun asDatabaseFilePath(): DatabaseTuneFilePath {
        return DatabaseTuneFilePath(
            id = id!!,
            tabLocalUrl = tabLocalUrl
        )
    }

    fun contents(): Tune {
        return Tune.from(this, null)
    }

    companion object {
        fun from(tune: Tune, withId: Long?): Tune {
            return Tune(
                id = withId,
                name = tune.name,
                tabWebUrl = tune.tabWebUrl,
                tabLocalUrl = tune.tabLocalUrl,
                progress = tune.progress,
                downloadComplete = tune.downloadComplete
            )
        }
    }
}
