package com.ijikod.apptasker.domain.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ijikod.apptasker.R
import com.ijikod.apptasker.data.Result
import com.ijikod.apptasker.data.models.Task
import com.ijikod.apptasker.data.repository.TaskRepository
import com.ijikod.apptasker.domain.AddTaskInteractor
import kotlinx.coroutines.launch
import java.util.*

class AddTaskViewModel @ViewModelInject constructor(
    private val taskRepository: TaskRepository,
    private val addTaskUseCase: AddTaskInteractor
) : ViewModel() {

    val title = MutableLiveData<String>()

    val description = MutableLiveData<String>()

    val taskDescriptionErrors = addTaskUseCase.taskDescriptionErrorObservable

    private val _errorMsg = MutableLiveData<Int>()
    val errorMsg: LiveData<Int> = _errorMsg

    private val _taskUpdated = MutableLiveData<Boolean>()
    val taskUpdated = _taskUpdated

    private val _result = MutableLiveData<Result<Task>>()
    val result = _result


    private var taskId: String? = null

    private var isNewTask: Boolean = false

    private var taskComplete = false


    fun load(taskId: String?) {
        this.taskId = taskId
        // TODO: see if this implementation can be improved

        this.taskId?.let {
            isNewTask = false

            viewModelScope.launch {
                taskRepository.getTask(this@AddTaskViewModel.taskId!!).let { result ->
                    if (result is Result.Success) {
                        onTaskLoaded(result.data)
                    }
                }
            }

        } ?: run {
            isNewTask = true
        }
    }


    private fun onTaskLoaded(task: Task) {
        title.value = task.title
        description.value = task.description
        taskComplete = task.completed
    }

    fun saveTask() {
        val currentTitle = title.value
        val currentDesc = description.value

        if (currentTitle.isNullOrEmpty() || currentDesc.isNullOrEmpty()) {
            _errorMsg.value = R.string.empty_fields_txt
            return
        }


        viewModelScope.launch {
            if (isNewTask) {
                val newTask = Task(
                    title = currentTitle,
                    description = currentDesc,
                    createdDate = addTaskUseCase.getCurrentDate()
                )
                taskRepository.createTask(newTask)
                _result.value = Result.Success(newTask)
            } else {
                val existingTask = Task(
                    id = taskId!!,
                    description = currentDesc,
                    title = currentTitle
                )
                taskRepository.updateTask(existingTask)
                _result.value = Result.Success(existingTask)
            }
        }


    }


}