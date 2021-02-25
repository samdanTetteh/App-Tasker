package com.ijikod.apptasker.presentation

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.ijikod.apptasker.domain.AddTaskInteractor

@BindingAdapter("app:titleErrorMsg")
fun setErrorMsg(view: TextInputLayout, error: AddTaskInteractor.AddTaskErrors){
    when (error){
        is AddTaskInteractor.AddTaskErrors.Empty -> view.error = view.context.getString(error.value)
        is AddTaskInteractor.AddTaskErrors.Invalid -> view.error = view.context.getString(error.value)
    }
}