package com.ijikod.apptasker.domain.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ijikod.apptasker.R
import com.ijikod.apptasker.data.Result
import com.ijikod.apptasker.data.models.Task
import com.ijikod.apptasker.data.repository.TasksRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel to task list Screen
 *
 */
class TasksViewModel @Inject constructor (
    private val tasksRepository: TasksRepository
): ViewModel() {

    private val _taskItems = MutableLiveData<List<Task>>().apply { value = emptyList() }
    val tasks: LiveData<List<Task>> = _taskItems

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _errorMsg = MutableLiveData<Int>()
    val errorMsg: LiveData<Int> = _errorMsg



    init {
        loadData()
    }



    private fun loadData(){
        _dataLoading.value = true

        viewModelScope.launch {
            val tasksResult = tasksRepository.getLocalTasks()

            if (tasksResult is Result.Success){
                val tasks = tasksResult.data
                _taskItems.value = tasks

                _dataLoading.value = false
            }else {
                _taskItems.value = emptyList()
                _errorMsg.value = R.string.task_loading_error
            }

            _dataLoading.value = false
        }
    }
}