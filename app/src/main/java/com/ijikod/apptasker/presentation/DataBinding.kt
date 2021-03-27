package com.ijikod.apptasker.presentation

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ijikod.apptasker.data.models.Task


@BindingAdapter("items")
fun setItems(listView: RecyclerView, items: List<Task>?) {
    items?.let {
        (listView.adapter as TaskAdapter).submitList(items)
    }

}