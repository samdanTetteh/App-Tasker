package com.ijikod.apptasker.data.repository

import com.ijikod.apptasker.data.Result
import com.ijikod.apptasker.data.models.Task
import com.ijikod.apptasker.data.source.persistance.TasksLocalDataSource
import com.ijikod.apptasker.di.ApplicationModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class TasksRepository @Inject constructor (
    @ApplicationModule.LocalDataSource private val localDataSource: TasksLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : Repository {


    @ExperimentalCoroutinesApi
    override suspend fun getTasks(): Flow<Result<List<Task>>> {
        return localTasks
    }


    override suspend fun saveTask(task: Task) {
        TODO("Not yet implemented")
    }


    @ExperimentalCoroutinesApi
    private val localTasks : Flow<Result<List<Task>>> =
        localDataSource.getTasks().flowOn(ioDispatcher).conflate()


}

