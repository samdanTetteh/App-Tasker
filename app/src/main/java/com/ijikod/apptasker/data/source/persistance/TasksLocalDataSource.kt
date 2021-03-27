package com.ijikod.apptasker.data.source.persistance

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ijikod.apptasker.data.Result
import com.ijikod.apptasker.data.models.Task
import com.ijikod.apptasker.data.source.TaskDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext


class TasksLocalDataSource(
    private val dao: TaskDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TaskDataSource {


    override fun observeTasks(): LiveData<Result<List<Task>>> {
        return Transformations.map(dao.observeTasks()) {
            Result.Success(it)
        }
    }

    override suspend fun getTasks(): Result<List<Task>> = withContext(ioDispatcher){
       return@withContext try {
           Result.Success(dao.getTasks())
       }catch (e: Exception) {
            Result.Error(e)
       }
    }

    override suspend fun getTask(taskId: String): Result<Task> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(dao.getTask(taskId))
        }catch (e: Exception) {
            Result.Error(e)
        }
    }


    override suspend fun findTaskByTitle(title: String): Flow<Result<List<Task>>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTask(task: Task) = withContext(ioDispatcher) {
        dao.insertTask(task)
    }

    override suspend fun deleteTask(taskId: String) = withContext<Unit>(ioDispatcher) {
        dao.deleteTaskById(taskId)
    }

    override suspend fun update(task: Task): Result<Int> = withContext(ioDispatcher) {
        return@withContext try{
            Result.Success(dao.updateTask(task))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getCompletedTasks(status: Boolean): Flow<Result<List<Task>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUnCompletedTasks(status: Boolean): Flow<Result<List<Task>>> {
        TODO("Not yet implemented")
    }


}