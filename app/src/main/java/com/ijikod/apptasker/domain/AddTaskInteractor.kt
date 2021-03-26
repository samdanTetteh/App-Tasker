package com.ijikod.apptasker.domain


import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import com.ijikod.apptasker.R
import com.ijikod.apptasker.util.Extentions.toString
import java.util.*
import javax.inject.Inject

class AddTaskInteractor @Inject constructor() {


    //todo: find a much suitable table type
    val taskTitleErrorObservable = ObservableField<AddTaskErrors>()
    val taskDescriptionErrorObservable = ObservableArrayList<AddTaskErrors>()


    /**
     * Task title validation
     * @param title as task description
     */
    fun taskTextValidation(title: String) : Int {
        return when {
            title.isEmpty() -> R.string.empty_title_error
            title.length < 5 -> R.string.max_character_txt
            else -> {
                0
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


    /**
     * Get current system date
     */
     fun getCurrentDate(): String {
        val currentDate = Calendar.getInstance().time
        return currentDate.toString("yyyy/MM/dd HH:mm:ss")
    }


    sealed class AddTaskErrors {
        data class Invalid(val value: Int) : AddTaskErrors()
        data class Empty(val value: Int) : AddTaskErrors()
        object Valid: AddTaskErrors()
    }
}