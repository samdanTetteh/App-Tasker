package com.ijikod.apptasker.presentation.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.ijikod.apptasker.R
import com.ijikod.apptasker.data.Result
import com.ijikod.apptasker.databinding.FragmentAddTaskBinding
import com.ijikod.apptasker.domain.vm.AddTaskViewModel
import com.ijikod.apptasker.util.Extentions.setupSnackBar
import com.ijikod.apptasker.util.Extentions.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_task.*
import javax.inject.Inject

/**
 * Main UI to Add task screen. Users can add task title and description
 *
 */
@AndroidEntryPoint
class AddTaskFragment : Fragment(R.layout.fragment_add_task) {

    private val viewModel by viewModels<AddTaskViewModel> ()

    private val args : AddTaskFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view  = inflater.inflate(R.layout.fragment_add_task, container, false)
        val binding = FragmentAddTaskBinding.bind(view).apply {
            vm = viewModel
        }

        setHasOptionsMenu(true)
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpObservers()
        viewModel.load(args.taskId)
    }


    private fun setUpObservers(){
        viewModel.result.observe(viewLifecycleOwner, { result ->

            when (result) {
                is Result.Success -> {
                    val action  = AddTaskFragmentDirections.actionAddTaskFragmentToTaskListFragment(R.string.task_saved_txt)
                    findNavController().navigate(action)
                }

                is Result.Error -> {
                    val action  = AddTaskFragmentDirections.actionAddTaskFragmentToTaskListFragment(R.string.oops_error_txt)
                    findNavController().navigate(action)
                }

                else -> {}
            }

        })

        setUpSnackBar()
    }

    private fun setUpSnackBar(){
        view?.setupSnackBar(viewLifecycleOwner, viewModel.errorMsg, Snackbar.LENGTH_SHORT)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_task_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_task -> viewModel.saveTask()
        }
        return true
    }
}