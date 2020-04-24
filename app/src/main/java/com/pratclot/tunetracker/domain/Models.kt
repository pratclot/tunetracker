package com.pratclot.tunetracker.domain

import androidx.annotation.Nullable
import com.pratclot.tunetracker.database.DatabaseTune

data class Tune(
    val id: Long? = null,
    val name: String,
    val tabWebUrl: String = "https://tommyemmanuel.files.wordpress.com/2007/09/tommy-emmanuel-windy-and-warm.pdf",
    val tabLocalUrl: String = "file:///"
) {
    fun asDatabaseModel(): DatabaseTune {
        return DatabaseTune(
            name = name,
            tabWebUrl = tabWebUrl,
            tabLocalUrl = tabLocalUrl
        )
    }
}