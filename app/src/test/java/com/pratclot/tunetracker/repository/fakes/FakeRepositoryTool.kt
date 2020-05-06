package com.pratclot.tunetracker.repository.fakes

import com.pratclot.tunetracker.domain.Tune
import com.pratclot.tunetracker.repository.IRepositoryTool

class FakeRepositoryTool : IRepositoryTool {
    override suspend fun getLocalPathTo(tune: Tune): String {
        return tune.tabLocalUrl
    }
}
