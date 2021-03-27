package com.ijikod.apptasker.data.repository

import androidx.lifecycle.LiveData
import com.ijikod.apptasker.data.Result
import com.ijikod.apptasker.data.models.Task
import kotlinx.coroutines.flow.Flow


/**
 * Interface to data layer
 *
 */
interface TaskRepository {

    fun observeTasks(): LiveData<Result<List<Task>>>

    suspend fun getTasks(): Result<List<Task>>

    suspend fun getTask(taskId: String): Result<Task>

    suspend fun createTask(task: Task)

    suspend fun deleteTask(taskId: String)

    suspend fun updateTask(task: Task): Result<Int>

}