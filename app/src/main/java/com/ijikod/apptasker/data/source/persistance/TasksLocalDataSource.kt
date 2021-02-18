package com.ijikod.apptasker.data.source.persistance

import com.ijikod.apptasker.data.source.persistance.TaskDao
import com.ijikod.apptasker.data.source.TaskDataSource
import kotlinx.coroutines.CoroutineDispatcher

class TasksLocalDataSource (
    dao: TaskDao,
    ioDispatcher: CoroutineDispatcher
) : TaskDataSource {
}