package com.ijikod.apptasker.domain

import androidx.databinding.ObservableArrayList
import com.ijikod.apptasker.R

class AddTaskInteractor {


    //todo: find a much suitable table type
    val taskTitleErrorObservable = ObservableArrayList<AddTaskErrors>()
    val taskDescriptionErrorObservable = ObservableArrayList<AddTaskErrors>()


    /**
     * Task title validation
     * @param title as task description
     */
    fun taskTitleTaskValidation(title: String) {
        when {
            title.isEmpty() -> taskTitleErrorObservable.add(0, AddTaskErrors.Empty(R.string.empty_title_error))
            title.length < 5 -> taskTitleErrorObservable.add(0, AddTaskErrors.Invalid(R.string.max_character_txt))
            else -> {
                taskTitleErrorObservable.clear()
            }
        }
    }


    /**
     * Task desc validation
     * @param desc as task description
     */
    fun taskDescTaskValidation(desc: String) {
        when {
            desc.isEmpty() -> taskDescriptionErrorObservable.add(0, AddTaskErrors.Empty(R.string.description_empty_txt))
            desc.length < 5 -> taskDescriptionErrorObservable.add(0, AddTaskErrors.Invalid(R.string.max_character_txt))
            else -> {
                taskDescriptionErrorObservable.clear()
            }
        }
    }


    sealed class AddTaskErrors {
        data class Invalid(val value: Int) : AddTaskErrors()
        data class Empty(val value: Int) : AddTaskErrors()
    }
}