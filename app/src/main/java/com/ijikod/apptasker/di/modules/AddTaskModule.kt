package com.ijikod.apptasker.di.modules

import androidx.lifecycle.ViewModel
import com.ijikod.apptasker.di.factory.ViewModelBuilder
import com.ijikod.apptasker.di.factory.ViewModelKey
import com.ijikod.apptasker.domain.vm.AddTaskViewModel
import com.ijikod.apptasker.domain.vm.TasksViewModel
import com.ijikod.apptasker.presentation.fragments.AddTaskFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Dagger Module for the Add Task feature
 *
 */
@Module
abstract class AddTaskModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun addTaskDetailFragment(): AddTaskFragment

    @Binds
    @IntoMap
    @ViewModelKey(AddTaskViewModel::class)
    abstract fun bindViewModel(viewModel: AddTaskViewModel): ViewModel

}