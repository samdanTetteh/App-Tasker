package com.ijikod.apptasker.presentation

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.ijikod.apptasker.data.models.Task
import com.ijikod.apptasker.domain.AddTaskInteractor

@BindingAdapter("titleErrorMsg")
fun setErrorMsg(view: TextInputLayout, error: AddTaskInteractor.AddTaskErrors){
    when (error){
        is AddTaskInteractor.AddTaskErrors.Empty -> view.error = view.context.getString(error.value)
        is AddTaskInteractor.AddTaskErrors.Invalid -> view.error = view.context.getString(error.value)
    }
}


@BindingAdapter("items")
fun setItems(listView: RecyclerView, items: List<Task>){
    (listView.adapter as TaskAdapter).submitList(items)
}