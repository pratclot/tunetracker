package com.pratclot.tunetracker.repository

import com.pratclot.tunetracker.domain.Tune

interface IRepositoryTool {
    suspend fun getLocalPathTo(tune: Tune, withDelete: Boolean = false): String
}
