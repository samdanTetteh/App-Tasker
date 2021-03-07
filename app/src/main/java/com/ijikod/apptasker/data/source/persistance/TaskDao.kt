package com.ijikod.apptasker.data.source.persistance

import androidx.room.*
import com.ijikod.apptasker.data.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged


/**
 * Data Access Object for tasks
 *
 */
@Dao
interface TaskDao {

    @Query("SELECT * FROM Tasks")
    suspend fun getTasks(): List<Task>

    @Query("SELECT * FROM TASKS WHERE id = :taskId")
    suspend fun getTask(taskId: String): Task

    @Query("SELECT * FROM Tasks WHERE title LIKE :title ORDER BY title DESC ")
    suspend fun findTaskByTile(title: String): List<Task>

    @Query("SELECT * FROM Tasks WHERE completed = :status")
    suspend fun getCompletedTask(status: Boolean): List<Task>

    @Query("SELECT * FROM Tasks WHERE completed = :status")
    suspend fun getUnCompletedTask(status: Boolean): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task): Int

    @Query("DELETE FROM Tasks where id = :id")
    suspend fun deleteTaskById(id: Int): Int

    @Delete
    fun deleteTask(vararg task: Task)

}