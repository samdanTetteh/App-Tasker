package com.ijikod.apptasker.domain.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ijikod.apptasker.R
import com.ijikod.apptasker.data.Result
import com.ijikod.apptasker.data.models.Task
import com.ijikod.apptasker.data.repository.TasksRepository
import kotlinx.coroutines.launch

/**
 * ViewModel to task list Screen
 *
 */
class TasksViewModel @ViewModelInject constructor (
    private val tasksRepository: TasksRepository
): ViewModel() {

    private val _loadTasks = MutableLiveData(false)

    private val _taskItems : LiveData<List<Task>> = Transformations.switchMap(_loadTasks) { loadTasks ->
        if (loadTasks){
            _dataLoading.value = true
            viewModelScope.launch {
                tasksRepository.getTasks()
                _dataLoading.value = false
            }
        }
        Transformations.distinctUntilChanged(tasksRepository.observeTasks()).switchMap { loadData(it) }
    }
    val tasks: LiveData<List<Task>> = _taskItems

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _errorMsg = MutableLiveData<Int>()
    val errorMsg: LiveData<Int> = _errorMsg

    val empty = Transformations.map(_taskItems) {
        it.isEmpty()
    }


    private val _newTaskEvent = MutableLiveData<Unit>()
    val newTaskEvent: LiveData<Unit> = _newTaskEvent

    private val _addTaskEvent = MutableLiveData<String>()
    val addTaskData: LiveData<String> = _addTaskEvent



    init {
        _loadTasks.value = true
    }


    private fun loadData(taskResult: Result<List<Task>>) : LiveData<List<Task>> {

        val result = MutableLiveData<List<Task>>()

            if (taskResult is Result.Success){
                result.value = taskResult.data
            }else {
                result.value = emptyList()
                _errorMsg.value = R.string.task_loading_error
            }

        return result
    }

    fun newTask(){
        _newTaskEvent.value = Unit
    }


    fun addTask(taskId: String){
        _addTaskEvent.value = taskId
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            tasksRepository.deleteTask(taskId)
        }
    }
}