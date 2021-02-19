package com.ijikod.apptasker.data.repository

import com.ijikod.apptasker.data.Result
import com.ijikod.apptasker.data.models.Task
import com.ijikod.apptasker.data.source.persistance.TasksLocalDataSource
import com.ijikod.apptasker.di.ApplicationModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class TasksRepository @Inject constructor (
    @ApplicationModule.LocalDataSource private val localDataSource: TasksLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : Repository {



    override suspend fun getTasks(): Result<List<Task>> {

        return withContext(ioDispatcher){

            val tasks = getLocalTasks()
            (tasks as? Result.Success)?.let { result ->
                return@withContext  Result.Success(result.data)
            }

            return@withContext Result.Error(Exception("Illegal state"))
        }


    }


    override suspend fun saveTask(task: Task) {
        TODO("Not yet implemented")
    }

    
    suspend fun getLocalTasks(): Result<List<Task>>{
        val localTasks = localDataSource.getTasks()
        if (localTasks is Result.Success) return localTasks

        //TODO: move error string to resource file
        return Result.Error(Exception("Error Fetching data from database"))
    }


}

