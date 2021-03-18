package com.ijikod.apptasker.data.repository

import com.ijikod.apptasker.data.Result
import com.ijikod.apptasker.data.models.Task
import com.ijikod.apptasker.data.source.TaskDataSource
import com.ijikod.apptasker.data.source.persistance.TasksLocalDataSource
import com.ijikod.apptasker.di.modules.ApplicationModule
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class TasksRepository @Inject constructor(
        @ApplicationModule.LocalDataSource private val localDataSource: TaskDataSource,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TaskRepository {


    override suspend fun getTasks(): Result<List<Task>> {

        return withContext(ioDispatcher) {

            val tasks = getLocalTasks()
            (tasks as? Result.Success)?.let { result ->
                return@withContext result
            }

            (tasks as? Result.Error)?.let { error ->
                return@withContext error
            }

            return@withContext Result.Error(Exception("Illegal state"))
        }


    }

    override suspend fun getTask(taskId: String): Result<Task> {
        return withContext(ioDispatcher) {
            val task = localDataSource.getTask(taskId)
            (task as? Result.Success)?.let { result ->
                return@withContext result
            }

            (task as? Result.Error)?.let { error ->
                return@withContext error
            }

            return@withContext Result.Error(Exception("Illegal State"))
        }
    }


    override suspend fun createTask(task: Task) {
        coroutineScope {
            localDataSource.saveTask(task)
        }
    }

    override suspend fun deleteTask(taskId: String) {
        coroutineScope {
            localDataSource.deleteTask(taskId)
        }
    }

    override suspend fun updateTask(task: Task): Result<Int> {
        return withContext(ioDispatcher) {
            val status = localDataSource.update(task)
            (status as? Result.Success)?.let { result ->
                return@withContext result
            }

            (status as? Result.Error)?.let { error ->
                return@withContext error
            }

            //TODO: move error string to resource file
            return@withContext Result.Error(Exception("Illegal State"))
        }
    }


    private suspend fun getLocalTasks(): Result<List<Task>> {
        val localTasks = localDataSource.getTasks()
        if (localTasks is Result.Success) return localTasks

        //TODO: move error string to resource file
        return Result.Error(Exception("Error Fetching data from database"))
    }


}

