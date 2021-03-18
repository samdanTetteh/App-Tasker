package com.ijikod.apptasker.presentation.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ijikod.apptasker.R
import com.ijikod.apptasker.data.Result
import com.ijikod.apptasker.databinding.FragmentAddTaskBinding
import com.ijikod.apptasker.domain.vm.AddTaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Main UI to Add task screen. Users can add task title and description
 *
 */
@AndroidEntryPoint
class AddTaskFragment : Fragment(R.layout.fragment_add_task) {

    private lateinit var viewDataBinding: FragmentAddTaskBinding

    private val viewModel by viewModels<AddTaskViewModel> ()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        setUpObservers()
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    private fun setUpObservers(){
        viewModel.result.observe(viewLifecycleOwner, { result ->

            when (result) {
                is Result.Success -> {
                    showToast(getString(R.string.task_saved_txt))
                    findNavController().navigateUp()
                }

                is Result.Error -> {
                    showToast(getString(R.string.oops_error_txt))
                }

                else -> {}
            }

        })
    }


    private fun showToast(msg: String){
        Toast.makeText(this.context, msg, Toast.LENGTH_LONG).show()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_task -> viewModel.saveTask()
        }
        return super.onOptionsItemSelected(item)
    }
}