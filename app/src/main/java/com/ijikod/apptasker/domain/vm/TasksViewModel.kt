package com.ijikod.apptasker.domain.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ijikod.apptasker.Event
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

    val empty = Transformations.map(_taskItems) {
        it.isEmpty()
    }

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _errorMsg = MutableLiveData<Int>()
    val errorMsg: LiveData<Int> = _errorMsg

    private val _snackBarMsg = MutableLiveData<Event<Int>>()
    val snackBarMsg = _snackBarMsg

    private val _newTaskEvent = MutableLiveData<Event<Unit>>()
    val newTaskEvent: LiveData<Event<Unit>> = _newTaskEvent

    private val _addTaskEvent = MutableLiveData<Event<String>>()
    val addTaskData: LiveData<Event<String>> = _addTaskEvent

    private var msgShown: Boolean = false



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
        _newTaskEvent.value = Event(Unit)
    }


    fun addTask(taskId: String){
        _addTaskEvent.value = Event(taskId)
    }

    fun showSnackBarMsg(msg: Int) {
        if (msgShown) return
        _snackBarMsg.value = Event(msg)
        msgShown = true
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            tasksRepository.deleteTask(taskId)
        }
    }
}