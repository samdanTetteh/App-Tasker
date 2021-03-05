package com.ijikod.apptasker.domain.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ijikod.apptasker.data.Result
import com.ijikod.apptasker.data.models.Task
import com.ijikod.apptasker.data.repository.TasksRepository
import com.ijikod.apptasker.domain.AddTaskInteractor
import com.ijikod.apptasker.util.Extentions.toString
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class AddTaskViewModel @Inject constructor(
        private val taskRepository: TasksRepository,
        private val addTaskUseCase: AddTaskInteractor
) : ViewModel() {

    val title = MutableLiveData<String>()

    val description = MutableLiveData<String>()

    val taskTitleErrors = addTaskUseCase.taskTitleErrorObservable
    val taskDescriptionErrors = addTaskUseCase.taskDescriptionErrorObservable

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg = _errorMsg

    private val _taskUpdated = MutableLiveData<Boolean>()
    val taskUpdated = _taskUpdated

    private val _result = MutableLiveData<Result<*>>()
    val result = _result


    private lateinit var task: Task

    private var isNewTask: Boolean = false

    private var taskComplete = false


    fun validateFields() {
        title.value?.let { title ->
            addTaskUseCase.taskTitleTaskValidation(title)
        }

        description.value?.let { description ->
            addTaskUseCase.taskDescTaskValidation(description)
        }
    }


    fun load(task: Task) {
        this.task = task
        // TODO: see if this implementation can be improved
        when {
            this.task.title.isEmpty() -> {
                isNewTask = true
                onTaskLoaded(this.task)
            }

            this.task.title.isNotEmpty() -> {
                isNewTask = false

                viewModelScope.launch {
                    taskRepository.getTask(this@AddTaskViewModel.task.id).let { result ->
                        if (result is Result.Success) {
                            onTaskLoaded(result.data)
                        }
                    }
                }
            }
        }
    }


    private fun onTaskLoaded(task: Task) {
        title.value = task.title
        description.value = task.description
        taskComplete = task.completed
    }

     fun saveTask() {
        val currentTitle = title.value!!
        val currentDesc = description.value!!

        viewModelScope.launch {
            if (isNewTask) {
               _result.value = taskRepository.createTask(
                        Task(
                                title = currentTitle,
                                description = currentDesc,
                                createdDate = getCurrentDate()
                        )
                )
            } else {
               _result.value = taskRepository.updateTask(
                        this@AddTaskViewModel.task.copy(
                                description = currentDesc,
                                title = currentTitle
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