package com.ijikod.apptasker.data.repository

import com.ijikod.apptasker.data.Result
import com.ijikod.apptasker.data.models.Task
import kotlinx.coroutines.flow.Flow


/**
 * Interface to data layer
 *
 */
interface Repository {

    suspend fun getTasks(): Flow<Result<List<Task>>>

    suspend fun saveTask(task: Task)

}