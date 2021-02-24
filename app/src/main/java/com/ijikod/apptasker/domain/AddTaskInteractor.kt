package com.ijikod.apptasker.domain

import androidx.databinding.ObservableArrayList

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
            title.isEmpty() -> taskTitleErrorObservable.add(0, AddTaskErrors.Empty("Title cannot be empty"))
            title.length < 5 -> taskTitleErrorObservable.add(0, AddTaskErrors.Invalid("Max 5"))
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
            desc.isEmpty() -> taskDescriptionErrorObservable.add(0, AddTaskErrors.Empty("Description cannot be empty"))
            desc.length < 5 -> taskDescriptionErrorObservable.add(0, AddTaskErrors.Invalid("Must be more than 5 characters"))
            else -> {
                taskDescriptionErrorObservable.clear()
            }
        }
    }


    sealed class AddTaskErrors {
        data class Invalid(val value: String) : AddTaskErrors()
        data class Empty(val value: String) : AddTaskErrors()
    }
}