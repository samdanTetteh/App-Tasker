package com.ijikod.apptasker.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ijikod.apptasker.R
import com.ijikod.apptasker.databinding.FragmentTaskListBinding
import com.ijikod.apptasker.domain.vm.TasksViewModel
import com.ijikod.apptasker.presentation.TaskAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_task_list.*
import javax.inject.Inject

class TaskListFragment : DaggerFragment(R.layout.fragment_task_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<TasksViewModel> { viewModelFactory }

    lateinit var binding: FragmentTaskListBinding

    lateinit var listAdapter: TaskAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(inflater, container, false).apply {
            vm = viewModel
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set the lifecycle owner to the lifecycle of the view
        binding.lifecycleOwner = this.viewLifecycleOwner
        setUpListAdapter()
        setUpNavigation()
//        setUpFab()
    }

    private fun setUpFab(){
        fab.setOnClickListener {
            navigateToNewTask()
        }
    }


    private fun setUpNavigation() {
        viewModel.newTaskEvent.observe(viewLifecycleOwner, Observer {
            navigateToNewTask()
        })

        viewModel.addTaskData.observe(viewLifecycleOwner, Observer {
            openTaskDetail(it)
        })
    }



    private fun navigateToNewTask(){
        findNavController().navigate(TaskListFragmentDirections.actionMainFragmentToAddTaskFragment())
    }

    private fun openTaskDetail(taskId: String){
        val action = TaskListFragmentDirections.actionMainFragmentToAddTaskFragment()
        val bundle = Bundle()
        bundle.putString("task_id", taskId)
    }


    private fun setUpListAdapter(){
        val viewModel = binding.vm
        viewModel?.let { viewModel ->
            listAdapter = TaskAdapter(viewModel)
            binding.taskListView.adapter = listAdapter
        } ?: run {
            println("ViewModel on initialized when attempting to create list Adapter")
        }
    }





}