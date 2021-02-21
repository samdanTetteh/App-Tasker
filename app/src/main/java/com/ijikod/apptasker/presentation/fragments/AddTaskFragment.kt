package com.ijikod.apptasker.presentation.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ijikod.apptasker.R
import com.ijikod.apptasker.databinding.FragmentAddTaskBinding
import com.ijikod.apptasker.domain.vm.AddTaskViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Main UI to Add task screen. Users can add task title and description
 *
 */
class AddTaskFragment : DaggerFragment(R.layout.fragment_add_task) {

    private lateinit var viewDataBinding: FragmentAddTaskBinding


    @Inject
    lateinit var viewModelFactory:
    ViewModelProvider.Factory
    private val viewModel by viewModels<AddTaskViewModel> { viewModelFactory }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return super.onCreateView(inflater, container, savedInstanceState)
    }




}