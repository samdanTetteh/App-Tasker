package com.ijikod.apptasker.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ijikod.apptasker.data.models.Task
import com.ijikod.apptasker.databinding.FragmentAddTaskBinding
import com.ijikod.apptasker.databinding.FragmentTaskListBinding
import com.ijikod.apptasker.databinding.TaskItemBinding
import com.ijikod.apptasker.domain.vm.TasksViewModel

class TaskAdapter(private val viewModel: TasksViewModel):
        ListAdapter<Task, TaskViewHolder>(TaskDiffCallBack()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(viewModel, task)
    }



}


class TaskViewHolder(private val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(viewModel: TasksViewModel, item: Task){
                binding.vm = viewModel
                binding.task = item
                binding.executePendingBindings()
            }

    companion object {
        fun from(parent: ViewGroup): TaskViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = TaskItemBinding.inflate(layoutInflater, parent, false)

            return TaskViewHolder(binding)
        }
    }

}



/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class TaskDiffCallBack: DiffUtil.ItemCallback<Task>() {

    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem == newItem

}