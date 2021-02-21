package com.ijikod.apptasker.domain.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ijikod.apptasker.data.Result
import com.ijikod.apptasker.data.models.Task
import com.ijikod.apptasker.data.repository.TaskRepository
import com.ijikod.apptasker.data.repository.TasksRepository
import com.ijikod.apptasker.util.Extentions.toString
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class AddTaskViewModel @Inject constructor(
        private val taskRepository: TaskRepository
) : ViewModel() {

    val title = MutableLiveData<String>()

    val description = MutableLiveData<String>()

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg = _errorMsg

    private val _taskUpdated = MutableLiveData<Boolean>()
    val taskUpdated = _taskUpdated


    private var taskId: String? = null

    private var isNewTask: Boolean = false

    private var taskComplete = false


    fun load(taskId: String) {
        this.taskId = taskId
        this.taskId?.let {
            isNewTask = false

            viewModelScope.launch {
                taskRepository.getTask(taskId).let { result ->
                    if (result is Result.Success) {
                        onTaskLoaded(result.data)
                    }
                }

            }


        } ?: run {
            isNewTask = true
            return

        }

    }


    private fun onTaskLoaded(task: Task) {
        title.value = task.title
        description.value = task.description
        taskComplete = task.completed
    }

    private fun saveTask() {
        val currentTitle = title.value!!
        val currentDesc = description.value!!

        if (isNewTask) {
            viewModelScope.launch {
                taskRepository.createTask(
                        Task(
                                title = currentTitle,
                                description = currentDesc,
                                createdDate = getCurrentDate()
                        )
                )
            }
        }
    }


    private fun getCurrentDate(): String {
        val currentDate = Calendar.getInstance().time
        return currentDate.toString("yyyy/MM/dd HH:mm:ss")
    }

}