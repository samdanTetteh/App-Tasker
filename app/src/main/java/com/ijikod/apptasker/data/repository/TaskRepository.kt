package com.ijikod.apptasker.data.repository

import com.ijikod.apptasker.data.Result
import com.ijikod.apptasker.data.models.Task
import kotlinx.coroutines.flow.Flow


/**
 * Interface to data layer
 *
 */
interface TaskRepository {

    suspend fun getTasks(): Result<Any>

    suspend fun getTask(taskId: String): Result<Task>

    suspend fun createTask(task: Task)

    suspend fun updateTask(task: Task): Result<Int>

}