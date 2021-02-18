package com.ijikod.apptasker.data.persistance

import androidx.room.*
import com.ijikod.apptasker.data.Models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged


/**
 * Data Access Object for tasks
 *
 */
@Dao
interface TaskDao {

    @Query("SELECT * FROM Tasks")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM Tasks WHERE title LIKE :title ORDER BY title DESC ")
    fun findTaskByTile(title: String): Flow<List<Task>>

    @Query("SELECT * FROM Tasks WHERE completed = :status")
    fun getCompletedTask(status: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM Tasks WHERE completed = :status")
    fun getUnCompletedTask(status: Boolean): Flow<List<Task>>

    @Insert
    fun insertTask(task: Task)

    @Update
    fun updateTask(task: Task): Int

    @Delete
    fun deleteTask(vararg task: Task)

    // Only emit the changes needed on completed tasks
    fun getCompletedDistinctUntilChanged(status: Boolean) =
        getCompletedTask(status).distinctUntilChanged()

    // Only emit the changes needed on uncompleted tasks
    fun getUncompletedTaskDistinctUntilChanged(status: Boolean) =
        getUnCompletedTask(status).distinctUntilChanged()


}