package com.ijikod.apptasker.presentation

import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.ijikod.apptasker.data.models.Task
import com.ijikod.apptasker.domain.vm.AddTaskViewModel


@BindingAdapter("items")
fun setItems(listView: RecyclerView, items: List<Task>) {
    (listView.adapter as TaskAdapter).submitList(items)
}