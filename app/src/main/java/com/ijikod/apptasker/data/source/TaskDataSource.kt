package com.ijikod.apptasker.data.source

import com.ijikod.apptasker.data.Result
import com.ijikod.apptasker.data.models.Task
import kotlinx.coroutines.flow.Flow


interface TaskDataSource {

    fun getTasks(): Flow<Result<List<Task>>>

    suspend fun findTaskByTitle(title: String): Flow<Result<List<Task>>>

    suspend fun saveTask(task: Task)

    suspend fun getCompletedTasks(status: Boolean): Flow<Result<List<Task>>>

    suspend fun getUnCompletedTasks(status: Boolean): Flow<Result<List<Task>>>

}
