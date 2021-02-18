package com.ijikod.apptasker.data.source.persistance

import com.ijikod.apptasker.data.Result
import com.ijikod.apptasker.data.models.Task
import com.ijikod.apptasker.data.source.TaskDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class TasksLocalDataSource(
    private val dao: TaskDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TaskDataSource {


    override fun getTasks(): Flow<Result<List<Task>>> = flow {
        dao.getTasks()
    }


    override suspend fun findTaskByTitle(title: String): Flow<Result<List<Task>>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTask(task: Task) {
        dao.insertTask(task)
    }

    override suspend fun getCompletedTasks(status: Boolean): Flow<Result<List<Task>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUnCompletedTasks(status: Boolean): Flow<Result<List<Task>>> {
        TODO("Not yet implemented")
    }


}