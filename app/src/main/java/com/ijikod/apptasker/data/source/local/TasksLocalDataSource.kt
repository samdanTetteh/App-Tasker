package com.ijikod.apptasker.data.source.local

import com.ijikod.apptasker.data.persistance.TaskDao
import com.ijikod.apptasker.data.source.TaskDataSource
import kotlinx.coroutines.CoroutineDispatcher

class TasksLocalDataSource (
    dao: TaskDao,
    ioDispatcher: CoroutineDispatcher
) : TaskDataSource {
}